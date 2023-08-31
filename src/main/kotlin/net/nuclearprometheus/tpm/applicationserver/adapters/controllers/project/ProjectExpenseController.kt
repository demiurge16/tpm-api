package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.project

import com.opencsv.bean.StatefulBeanToCsvBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.requests.ExpenseRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.responses.ExpenseResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.ProjectExpenseApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.ProjectExpenseRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.ErrorResponse
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.OutputStreamWriter
import java.io.PipedInputStream
import java.io.PipedOutputStream
import java.time.LocalDate
import java.util.*
import kotlin.concurrent.thread

@RestController
@RequestMapping("/api/v1/project/{projectId}/expense")
class ProjectExpenseController(private val service: ProjectExpenseApplicationService) {

    private val logger = loggerFor(ProjectExpenseController::class.java)

    @GetMapping("")
    fun getExpenses(@PathVariable(name = "projectId") projectId: UUID, query: ExpenseRequest.List) = with(logger) {
        info("GET /api/v1/project/$projectId/expense")

        ResponseEntity.ok().body(service.getExpensesForProject(projectId, query))
    }

    @GetMapping("/export", produces = ["text/csv"])
    fun export(@PathVariable(name = "projectId") projectId: UUID, query: ExpenseRequest.List) = with(logger) {
        info("GET /api/v1/project/$projectId/expense/export")

        val expenses = service.getExpensesForProject(projectId, query)

        val output = PipedOutputStream()
        val input = PipedInputStream(output)

        thread {
            val writer = OutputStreamWriter(output)
            val beanToCsv = StatefulBeanToCsvBuilder<ExpenseResponse.Expense>(writer).build()
            beanToCsv.write(expenses.items)
            writer.flush()
            writer.close()
            output.close()
        }

        val name = "project-$projectId-expenses-${LocalDate.now()}.csv"
        val resource = InputStreamResource(input)

        ResponseEntity.ok()
            .headers(
                HttpHeaders().apply {
                    add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=$name")
                    add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
                    add(HttpHeaders.PRAGMA, "no-cache")
                    add(HttpHeaders.EXPIRES, "0")
                }
            )
            .contentType(MediaType.parseMediaType("text/csv"))
            .body(resource)
    }

    @PostMapping("")
    fun createExpense(@PathVariable(name = "projectId") projectId: UUID, @RequestBody request: ProjectExpenseRequest.Create) =
        with(logger) {
            info("POST /api/v1/project/$projectId/expense")

            ResponseEntity.ok().body(service.createExpense(projectId, request))
        }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: NotFoundException) = with(logger) {
        warn("NotFoundException: ${e.message}")
        ResponseEntity.notFound().build<Void>()
    }

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(e: IllegalStateException) = with(logger) {
        warn("IllegalStateException: ${e.message}")
        ResponseEntity.badRequest().body(
            ErrorResponse(e.message ?: "Illegal state")
        )
    }
}


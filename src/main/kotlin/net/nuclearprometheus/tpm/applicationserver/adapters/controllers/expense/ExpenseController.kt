package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.expense

import com.opencsv.bean.StatefulBeanToCsvBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.ExpenseApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.responses.Expense as ExpenseResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.responses.ErrorResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.requests.ListExpenses
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
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
@RequestMapping("/api/v1/expense")
class ExpenseController(private val service: ExpenseApplicationService) {

    private val logger = loggerFor(ExpenseController::class.java)

    @GetMapping("")
    fun getExpenses(query: ListExpenses) = with(logger) {
        info("GET /api/v1/expense")
        ResponseEntity.ok().body(service.getExpenses(query))
    }

    @GetMapping("/export", produces = ["text/csv"])
    fun export(query: ListExpenses) = with(logger) {
        info("GET /api/v1/expense/export")

        val expenses = service.getExpenses(query)

        val output = PipedOutputStream()
        val input = PipedInputStream(output)

        thread {
            val writer = OutputStreamWriter(output)
            val beanToCsv = StatefulBeanToCsvBuilder<ExpenseResponse>(writer).build()
            beanToCsv.write(expenses.items)
            writer.flush()
            writer.close()
            output.close()
        }

        val name = "expenses-${LocalDate.now()}.csv"
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

    @GetMapping("/{expenseId}")
    fun getExpense(@PathVariable(name = "expenseId") expenseId: UUID) = with(logger) {
        info("GET /api/v1/expense/$expenseId")
        ResponseEntity.ok().body(service.getExpense(expenseId))
    }

    @DeleteMapping("/{expenseId}")
    fun deleteExpense(@PathVariable(name = "expenseId") expenseId: UUID) = with(logger) {
        info("DELETE /api/v1/expense/$expenseId")
        ResponseEntity.ok().body(service.deleteExpense(expenseId))
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: NotFoundException) = with(logger) {
        warn("NotFoundException: ${e.message}")
        ResponseEntity.notFound().build<Void>()
    }

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(e: IllegalStateException) = with(logger) {
        warn("IllegalStateException: ${e.message}")
        ResponseEntity.internalServerError().body(ErrorResponse(e.message ?: "Illegal state"))
    }
}

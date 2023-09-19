package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.dictionaries

import com.opencsv.bean.StatefulBeanToCsvBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.ExpenseCategoryApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.ExpenseCategoryRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.ExpenseCategoryResponse
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
@RequestMapping("/api/v1/expense-category")
class ExpenseCategoryController(private val service: ExpenseCategoryApplicationService) {

    private val logger = loggerFor(ExpenseCategoryController::class.java)

    @GetMapping("")
    fun getAll(query: ExpenseCategoryRequest.List) = with(logger) {
        info("GET /api/v1/expense-category")

        ResponseEntity.ok().body(service.getExpenseCategories(query))
    }

    @GetMapping("/export", produces = ["text/csv"])
    fun export(query: ExpenseCategoryRequest.List) = with(logger) {
        info("GET /api/v1/expense-category/export")

        val expenseCategories = service.getExpenseCategories(query)

        val output = PipedOutputStream()
        val input = PipedInputStream(output)

        thread {
            val writer = OutputStreamWriter(output)
            val beanToCsv = StatefulBeanToCsvBuilder<ExpenseCategoryResponse.ExpenseCategory>(writer).build()
            beanToCsv.write(expenseCategories.items)
            writer.flush()
            writer.close()
            output.close()
        }

        val name = "expense-categories-${LocalDate.now()}.csv"
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

    @GetMapping("/{expenseCategoryId}")
    fun get(@PathVariable(name = "expenseCategoryId") id: UUID) = with(logger) {
        info("GET /api/v1/expense-category/$id")

        ResponseEntity.ok().body(service.getExpenseCategory(id))
    }

    @PostMapping("")
    fun create(@RequestBody request: ExpenseCategoryRequest.Create) = with(logger) {
        info("POST /api/v1/expense-category")

        ResponseEntity.ok().body(service.createExpenseCategory(request))
    }

    @PutMapping("/{expenseCategoryId}")
    fun update(@PathVariable(name = "expenseCategoryId") id: UUID, @RequestBody request: ExpenseCategoryRequest.Update) = with(logger) {
        info("PUT /api/v1/expense-category/$id")

        ResponseEntity.ok().body(service.updateExpenseCategory(id, request))
    }

    @PatchMapping("/{expenseCategoryId}/activate")
    fun activate(@PathVariable(name = "expenseCategoryId") id: UUID) = with(logger) {
        info("PATCH /api/v1/expense-category/$id/activate")

        ResponseEntity.ok().body(service.activateExpenseCategory(id))
    }

    @PatchMapping("/{expenseCategoryId}/deactivate")
    fun deactivate(@PathVariable(name = "expenseCategoryId") id: UUID) = with(logger) {
        info("PATCH /api/v1/expense-category/$id/deactivate")

        ResponseEntity.ok().body(service.deactivateExpenseCategory(id))
    }
}


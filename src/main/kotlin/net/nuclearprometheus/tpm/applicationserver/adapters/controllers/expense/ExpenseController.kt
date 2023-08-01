package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.expense

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.ExpenseApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.ErrorResponse
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/expense")
class ExpenseController(private val service: ExpenseApplicationService) {

    private val logger = loggerFor(ExpenseController::class.java)

    @GetMapping("")
    fun getExpenses() = with(logger) {
        info("GET /api/v1/expense")

        ResponseEntity.ok().body(service.getExpenses())
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
        ResponseEntity.badRequest().body(
            ErrorResponse(e.message ?: "Illegal state")
        )
    }
}

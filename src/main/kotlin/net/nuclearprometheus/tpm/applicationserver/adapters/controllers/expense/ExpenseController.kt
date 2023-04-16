package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.expense

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.ExpenseApplicationService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/expense")
class ExpenseController(private val service: ExpenseApplicationService) {

    private val logger = loggerFor(ExpenseController::class.java)

    @GetMapping("")
    fun getExpenses() = with(logger) {
        info("GET /api/v1/expense")

        service.getExpenses()
    }

    @GetMapping("/{expenseId}")
    fun getExpense(@PathVariable(name = "expenseId") expenseId: UUID) = with(logger) {
        info("GET /api/v1/expense/$expenseId")

        service.getExpense(expenseId)
    }

    @DeleteMapping("/{expenseId}")
    fun deleteExpense(@PathVariable(name = "expenseId") expenseId: UUID) = with(logger) {
        info("DELETE /api/v1/expense/$expenseId")

        service.deleteExpense(expenseId)
    }
}


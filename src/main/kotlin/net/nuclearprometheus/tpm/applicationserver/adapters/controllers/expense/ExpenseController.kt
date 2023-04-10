package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.expense

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/expense")
class ExpenseController {

    @GetMapping("")
    fun getExpenses() {
        TODO()
    }

    @GetMapping("/{expenseId}")
    fun getExpense(@PathVariable(name = "expenseId") expenseId: UUID) {
        TODO()
    }

    @DeleteMapping("/{expenseId}")
    fun deleteExpense(@PathVariable(name = "expenseId") expenseId: UUID) {
        TODO()
    }
}

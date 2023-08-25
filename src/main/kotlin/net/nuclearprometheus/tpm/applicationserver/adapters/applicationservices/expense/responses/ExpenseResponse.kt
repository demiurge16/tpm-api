package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.responses

import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

sealed class ExpenseResponse {

    data class Expense(
        val id: UUID,
        val description: String,
        val category: ExpenseCategory,
        val amount: BigDecimal,
        val currency: Currency,
        val date: ZonedDateTime,
        val teamMember: Spender,
        val project: ProjectShortView
    ) : ExpenseResponse()
}
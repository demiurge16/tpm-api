package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.response

import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

sealed class ExpenseResponse {

    data class View(
        val id: UUID,
        val description: String,
        val category: ExpenseCategoryView,
        val amount: BigDecimal,
        val currency: CurrencyView,
        val date: ZonedDateTime,
        val teamMemberId: UUID,
        val projectId: UUID
    ) : ExpenseResponse() {

        data class ExpenseCategoryView(
            val id: UUID,
            val name: String,
            val description: String
        )

        data class CurrencyView(
            val code: String,
            val name: String
        )
    }
}
package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses

import java.util.*

sealed class ExpenseCategoryResponse {

    data class ExpenseCategory(
        val id: UUID,
        val name: String,
        val description: String,
        val active: Boolean
    ) : ExpenseCategoryResponse()

    data class Status(val id: UUID, val active: Boolean) : ExpenseCategoryResponse()
}
package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.response

import java.util.*

data class ExpenseCategory(
    val id: UUID,
    val name: String,
    val description: String
)

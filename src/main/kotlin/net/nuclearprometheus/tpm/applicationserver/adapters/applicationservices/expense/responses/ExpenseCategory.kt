package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.responses

import java.util.*

data class ExpenseCategory(
    val id: UUID,
    val name: String,
    val description: String
)

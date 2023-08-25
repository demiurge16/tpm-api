package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.responses

import java.util.*

data class Spender(
    val userId: UUID,
    val firstName: String,
    val lastName: String,
    val email: String
)

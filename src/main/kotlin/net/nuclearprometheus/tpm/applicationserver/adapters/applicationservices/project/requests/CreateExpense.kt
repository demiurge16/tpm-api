package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests

import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

data class CreateExpense(
    val description: String,
    val category: UUID,
    val amount: BigDecimal,
    val currency: String,
    val date: ZonedDateTime,
    val spenderId: UUID
)

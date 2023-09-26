package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests

import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

data class CreateTask(
    val title: String,
    val description: String,
    val sourceLanguage: String,
    val targetLanguage: String,
    val accuracyId: UUID,
    val industryId: UUID,
    val unitId: UUID,
    val serviceTypeId: UUID,
    val amount: Int,
    val expectedStart: ZonedDateTime,
    val deadline: ZonedDateTime,
    val budget: BigDecimal,
    val currencyCode: String,
    val priorityId: UUID
)

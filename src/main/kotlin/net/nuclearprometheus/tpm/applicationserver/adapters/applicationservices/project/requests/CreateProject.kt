package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests

import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

data class CreateProject(
    val title: String,
    val description: String,
    val sourceLanguage: String,
    val targetLanguages: List<String>,
    val accuracyId: UUID,
    val industryId: UUID,
    val unitId: UUID,
    val serviceTypeIds: List<UUID>,
    val amount: Int,
    val expectedStart: ZonedDateTime,
    val internalDeadline: ZonedDateTime,
    val externalDeadline: ZonedDateTime,
    val budget: BigDecimal,
    val currencyCode: String,
    val clientId: UUID
)
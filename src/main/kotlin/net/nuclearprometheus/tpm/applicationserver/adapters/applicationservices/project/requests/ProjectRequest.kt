package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests

import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

sealed class ProjectRequest {

    data class Create(
        val title: String,
        val description: String,
        val sourceLanguage: String,
        val targetLanguages: List<String>,
        val accuracyId: UUID,
        val industryId: UUID,
        val unitId: UUID,
        val amount: Int,
        val expectedStart: ZonedDateTime,
        val internalDeadline: ZonedDateTime,
        val externalDeadline: ZonedDateTime,
        val budget: BigDecimal,
        val currencyCode: String,
        val clientId: UUID
    ) : ProjectRequest()

    data class Update(
        val title: String,
        val description: String,
        val sourceLanguage: String,
        val targetLanguages: List<String>,
        val accuracyId: UUID,
        val industryId: UUID,
        val unitId: UUID,
        val amount: Int,
        val budget: BigDecimal,
        val currencyCode: String,
        val clientId: UUID
    ) : ProjectRequest()

    data class MoveStart(val expectedStart: ZonedDateTime) : ProjectRequest()

    data class MoveDeadline(val internalDeadline: ZonedDateTime, val externalDeadline: ZonedDateTime) : ProjectRequest()
}
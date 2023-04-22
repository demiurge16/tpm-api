package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Country
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CountryCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CurrencyCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectStatus
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

sealed class ProjectResponse {

    data class View(
        val id: UUID,
        val title: String,
        val description: String,
        val sourceLanguage: Language,
        val targetLanguages: List<Language>,
        val accuracy: Accuracy,
        val industry: Industry,
        val unit: Unit,
        val amount: Int,
        val expectedStart: ZonedDateTime,
        val internalDeadline: ZonedDateTime,
        val externalDeadline: ZonedDateTime,
        val budget: BigDecimal,
        val currency: Currency,
        val status: Status,
        val client: ClientResponse.Client
    ) : ProjectResponse()

    data class StartMoved(
        val id: UUID,
        val expectedStart: ZonedDateTime
    ) : ProjectResponse()

    data class DeadlineMoved(
        val id: UUID,
        val internalDeadline: ZonedDateTime,
        val externalDeadline: ZonedDateTime
    ) : ProjectResponse()
}

package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientResponse
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

sealed class ProjectResponse {

    data class Project(
        val id: UUID,
        val title: String,
        val description: String,
        val sourceLanguage: Language,
        val targetLanguages: List<Language>,
        val accuracy: Accuracy,
        val industry: Industry,
        val unit: Unit,
        val serviceTypes: List<ServiceType>,
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

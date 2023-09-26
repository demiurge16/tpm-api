package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.Client
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.*
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.Currency
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.Unit
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

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
    val status: ProjectStatus,
    val client: Client
)

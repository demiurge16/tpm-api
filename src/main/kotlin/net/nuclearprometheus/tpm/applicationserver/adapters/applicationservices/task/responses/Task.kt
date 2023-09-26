package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.*
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.Currency
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.Unit
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectShortView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.responses.User
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

data class Task(
    val id: UUID,
    val title: String,
    val description: String,
    val sourceLanguage: Language,
    val targetLanguage: Language,
    val accuracy: Accuracy,
    val industry: Industry,
    val unit: Unit,
    val serviceType: ServiceType,
    val amount: Int,
    val expectedStart: ZonedDateTime,
    val deadline: ZonedDateTime,
    val budget: BigDecimal,
    val currency: Currency,
    val status: TaskStatus,
    val priority: Priority,
    val assignee: User?,
    val project: ProjectShortView
)


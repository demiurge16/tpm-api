package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses

import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

sealed class TaskResponse {
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
        val status: Status,
        val priority: Priority,
        val assignee: Assignee?,
        val project: ProjectShortView
    ) : TaskResponse()

    data class StartMoved(val taskId: UUID, val start: ZonedDateTime) : TaskResponse()
    data class DeadlineMoved(val taskId: UUID, val deadline: ZonedDateTime) : TaskResponse()
}


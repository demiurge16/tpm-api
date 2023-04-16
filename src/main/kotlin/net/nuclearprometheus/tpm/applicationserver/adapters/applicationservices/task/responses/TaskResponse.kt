package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses

import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskStatus
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

sealed class TaskResponse {
    data class View(
        val id: UUID,
        val title: String,
        val description: String,
        val sourceLanguage: LanguageView,
        val targetLanguage: LanguageView,
        val accuracy: AccuracyView,
        val industry: IndustryView,
        val unit: UnitView,
        val amount: Int,
        val expectedStart: ZonedDateTime,
        val deadline: ZonedDateTime,
        val budget: BigDecimal,
        val currency: CurrencyView,
        val status: TaskStatusView,
        val priority: PriorityView,
        val assignee: AssigneeView?,
        val projectId: UUID
    ) : TaskResponse() {

        data class LanguageView(
            val code: String,
            val name: String
        )

        data class AccuracyView(
            val id: UUID,
            val name: String,
            val description: String,
        )

        data class IndustryView(
            val id: UUID,
            val name: String,
            val description: String,
        )

        data class UnitView(
            val id: UUID,
            val name: String,
            val description: String,
        )

        data class CurrencyView(
            val code: String,
            val name: String
        )

        data class TaskStatusView(
            val status: TaskStatus,
            val name: String,
            val description: String,
        )

        data class PriorityView(
            val id: UUID,
            val name: String,
            val description: String,
            val emoji: String,
            val value: Int
        )

        data class AssigneeView(
            val teamMemberId: UUID,
            val userId: UUID,
            val firstName: String,
            val lastName: String,
            val email: String,
        )
    }

    data class StartMoved(val taskId: UUID, val start: ZonedDateTime) : TaskResponse()
    data class DeadlineMoved(val taskId: UUID, val deadline: ZonedDateTime) : TaskResponse()
}
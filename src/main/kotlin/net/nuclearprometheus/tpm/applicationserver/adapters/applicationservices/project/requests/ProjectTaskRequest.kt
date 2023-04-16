package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests

import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

sealed class ProjectTaskRequest {
    data class Create(
        val title: String,
        val description: String,
        val sourceLanguage: String,
        val targetLanguage: String,
        val accuracy: UUID,
        val industry: UUID,
        val unit: UUID,
        val amount: Int,
        val expectedStart: ZonedDateTime,
        val deadline: ZonedDateTime,
        val budget: BigDecimal,
        val currency: String,
        val priorityId: UUID,
        val projectId: UUID
    ) : ProjectTaskRequest()
}
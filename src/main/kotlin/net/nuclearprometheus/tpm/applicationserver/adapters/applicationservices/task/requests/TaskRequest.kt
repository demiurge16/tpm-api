package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.requests

import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

sealed class TaskRequest {
    data class Update(
        val title: String,
        val description: String,
        val sourceLanguage: String,
        val targetLanguage: String,
        val accuracy: UUID,
        val industry: UUID,
        val unit: UUID,
        val amount: Int,
        val budget: BigDecimal,
        val currency: String,
    ) : TaskRequest()

    data class MoveStart(val start: ZonedDateTime) : TaskRequest()
    data class MoveDeadline(val deadline: ZonedDateTime) : TaskRequest()
}
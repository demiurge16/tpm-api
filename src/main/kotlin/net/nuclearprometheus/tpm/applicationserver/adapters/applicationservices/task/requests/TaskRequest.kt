package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.requests

import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

sealed class TaskRequest {

    class List(
        page: Int?,
        size: Int?,
        sort: String?,
        search: String?
    ) : FilteredRequest<Task>( page, size, sort, search) {

        override fun toString(): String {
            return "TaskRequest.List(page=$page, size=$size, sort=$sort, search=$search)"
        }
    }

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
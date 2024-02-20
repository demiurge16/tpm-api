package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses

import java.util.*

sealed class TimeEntryStatusResponse {

    data class NewStatus(val status: TimeEntryStatus, val timeEntryId: UUID) : TimeEntryStatusResponse()
}

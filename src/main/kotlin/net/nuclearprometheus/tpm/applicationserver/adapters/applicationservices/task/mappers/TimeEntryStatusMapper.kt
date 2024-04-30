package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TimeEntryStatus
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TimeEntryStatusResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TimeEntry

object TimeEntryStatusMapper {

    fun TimeEntry.toTimeEntryStatusResponse() = TimeEntryStatusResponse.NewStatus(
        timeEntryId = id.value,
        status = TimeEntryStatus(
            status = status,
            title = status.title,
            description = status.description
        )
    )
}

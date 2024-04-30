package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TimeEntryStatus
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TimeUnit
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.mappers.UserMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TimeEntry as TimeEntryResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TimeEntry

object TimeEntryMapper {

    fun TimeEntry.toView() = TimeEntryResponse(
        id = id.value,
        user = user.toView(),
        date = date,
        timeSpent = timeSpent,
        timeUnit = TimeUnit(
            unit = timeUnit,
            title = timeUnit.title,
            description = timeUnit.description
        ),
        status = TimeEntryStatus(
            status = status,
            title = status.title,
            description = status.description
        ),
        description = description,
        taskId = taskId.value
    )
}

package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TaskNewAssignee
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.mappers.UserMapper.toView
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task

object TaskAssignmentMapper {

    fun Task.toAssignee() = TaskNewAssignee(
        taskId = id.value,
        newAssignee = assignee?.toView()
    )
}
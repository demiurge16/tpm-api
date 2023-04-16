package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers.TaskPriorityMapper.toPriorityChangedView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.requests.TaskPriorityRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.PriorityId
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.task.TaskService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import java.util.*

@Service
class TaskPriorityApplicationService(private val service: TaskService) {

    private val logger = loggerFor(TaskPriorityApplicationService::class.java)

    fun changePriority(taskId: UUID, request: TaskPriorityRequest.ChangePriority) = with(logger) {
        info("changePriority($taskId, $request)")

        service.changePriority(TaskId(taskId), PriorityId(request.priorityId)).toPriorityChangedView()
    }
}
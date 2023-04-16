package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers.TaskRefdataMapper.toView
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskStatus
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service

@Service
class TaskRefdataApplicationService {

    private val logger = loggerFor(TaskRefdataApplicationService::class.java)
    fun getStatuses() = with(logger) {
        info("getStatuses()")

        TaskStatus.values().map { it.toView() }
    }
}
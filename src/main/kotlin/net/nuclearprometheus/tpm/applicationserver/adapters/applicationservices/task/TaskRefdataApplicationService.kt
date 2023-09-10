package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers.TaskRefdataMapper.toView
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskStatus
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(propagation = Propagation.REQUIRED)
class TaskRefdataApplicationService {

    private val logger = loggerFor(TaskRefdataApplicationService::class.java)

    fun getStatuses() = with(logger) {
        info("getStatuses()")

        TaskStatus.values().map { it.toView() }
    }
}
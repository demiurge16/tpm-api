package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TimeEntryStatus as TimeEntryStatusResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TimeUnit as TimeUnitResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers.TimeEntryRefdataMapper.toView
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TimeEntryStatus
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TimeUnit
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(propagation = Propagation.REQUIRED)
class TimeEntryRefdataApplicationService {

    private val logger = loggerFor(TimeEntryRefdataApplicationService::class.java)

    fun getTimeEntryStatuses(): List<TimeEntryStatusResponse> {
        logger.info("getTimeEntryStatuses()")
        return TimeEntryStatus.values().map { it.toView() }
    }

    fun getTimeEntryTimeUnits(): List<TimeUnitResponse> {
        logger.info("getTimeEntryTimeUnits()")
        return TimeUnit.values().map { it.toView() }
    }
}
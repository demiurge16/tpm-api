package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers.TimeEntryMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers.TimeEntryStatusMapper.toTimeEntryStatusResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.requests.UpdateTimeEntry
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TimeEntryStatusResponse
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TimeEntry
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TimeEntryId
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.specification.TimeEntrySpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.task.TimeEntryRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.task.TimeEntryService
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TimeEntry as TimeEntryResponse

@Service
@Transactional(propagation = Propagation.REQUIRED)
class TimeEntryApplicationService(
    private val service: TimeEntryService,
    private val repository: TimeEntryRepository,
    private val specificationBuilder: TimeEntrySpecificationBuilder
) {

    private val logger = loggerFor(TimeEntryApplicationService::class.java)

    fun getTimeEntries(query: FilteredRequest<TimeEntry>): Page<TimeEntryResponse> {
        logger.info("getTimeEntries($query)")
        return repository.get(query.toQuery(specificationBuilder)).map { it.toView() }
    }

    fun getTimeEntry(timeEntryId: UUID): TimeEntryResponse {
        logger.info("getTimeEntry($timeEntryId)")
        return repository.get(TimeEntryId(timeEntryId))?.toView() ?: throw NotFoundException("Time entry with id $timeEntryId not found")
    }

    fun updateTimeEntry(timeEntryId: UUID, request: UpdateTimeEntry): TimeEntryResponse {
        logger.info("updateTimeEntry($timeEntryId, $request)")
        return service.update(
            id = TimeEntryId(timeEntryId),
            userId = UserId(request.userId),
            date = request.date,
            timeSpent = request.timeSpent,
            timeUnit = request.timeUnit,
            description = request.description
        ).toView()
    }

    fun deleteTimeEntry(timeEntryId: UUID) {
        logger.info("deleteTimeEntry($timeEntryId)")
        service.delete(TimeEntryId(timeEntryId))
    }

    fun submitTimeEntry(timeEntryId: UUID): TimeEntryStatusResponse {
        logger.info("submitTimeEntry($timeEntryId)")
        return service.submit(TimeEntryId(timeEntryId)).toTimeEntryStatusResponse()
    }

    fun approveTimeEntry(timeEntryId: UUID): TimeEntryStatusResponse {
        logger.info("approveTimeEntry($timeEntryId)")
        return service.approve(TimeEntryId(timeEntryId)).toTimeEntryStatusResponse()
    }

    fun rejectTimeEntry(timeEntryId: UUID): TimeEntryStatusResponse {
        logger.info("rejectTimeEntry($timeEntryId)")
        return service.reject(TimeEntryId(timeEntryId)).toTimeEntryStatusResponse()
    }
}

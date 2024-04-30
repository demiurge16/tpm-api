package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers.TimeEntryMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.requests.CreateTimeEntry
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TimeEntry as TimeEntryResponse
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskId
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TimeEntry
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.specification.TimeEntrySpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.task.TimeEntryRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.task.TimeEntryService
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(propagation = Propagation.REQUIRED)
class TaskTimeEntryApplicationService(
    private val service: TimeEntryService,
    private val repository: TimeEntryRepository,
    private val specificationBuilder: TimeEntrySpecificationBuilder
) {

    private val logger = loggerFor(TaskTimeEntryApplicationService::class.java)

    fun getTimeEntriesForTask(taskId: UUID, query: FilteredRequest<TimeEntry>): Page<TimeEntryResponse> {
        logger.info("getTimeEntriesForTask($taskId)")
        return repository.getForTask(TaskId(taskId), query.toQuery(specificationBuilder)).map { it.toView() }
    }

    fun addTimeEntryToTask(taskId: UUID, request: CreateTimeEntry): TimeEntryResponse {
        logger.info("addTimeEntryToTask($taskId, $request)")
        return service.create(
            taskId = TaskId(taskId),
            userId = UserId(request.userId),
            date = request.date,
            timeSpent = request.timeSpent,
            timeUnit = request.timeUnit,
            description = request.description
        ).toView()
    }

    fun addSubmittedTimeEntryToTask(taskId: UUID, request: CreateTimeEntry): TimeEntryResponse {
        logger.info("addSubmittedTimeEntryToTask($taskId, $request)")
        return service.createSubmitted(
            taskId = TaskId(taskId),
            userId = UserId(request.userId),
            date = request.date,
            timeSpent = request.timeSpent,
            timeUnit = request.timeUnit,
            description = request.description
        ).toView()
    }
}
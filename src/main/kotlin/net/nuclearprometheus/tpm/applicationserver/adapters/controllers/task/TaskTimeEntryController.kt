package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.task

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.TaskTimeEntryApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.requests.CreateTimeEntry
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.requests.ListTimeEntries
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TimeEntry
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/task/{taskId}/time-entry")
class TaskTimeEntryController(private val service: TaskTimeEntryApplicationService) {

    private val logger = loggerFor(TaskTimeEntryController::class.java)

    @GetMapping("")
    fun getTimeEntries(@PathVariable(name = "taskId") taskId: UUID, query: ListTimeEntries): ResponseEntity<Page<TimeEntry>> {
        logger.info("GET /api/v1/task/$taskId/time-entry")
        return ResponseEntity.ok().body(service.getTimeEntriesForTask(taskId, query))
    }

    @PostMapping("")
    fun addTimeEntry(@PathVariable(name = "taskId") taskId: UUID, @RequestBody request: CreateTimeEntry): ResponseEntity<TimeEntry> {
        logger.info("POST /api/v1/task/$taskId/time-entry")
        return ResponseEntity.ok().body(service.addTimeEntryToTask(taskId, request))
    }

    @PostMapping("/submitted")
    fun addSubmittedTimeEntry(@PathVariable(name = "taskId") taskId: UUID, @RequestBody request: CreateTimeEntry): ResponseEntity<TimeEntry> {
        logger.info("POST /api/v1/task/$taskId/time-entry/submitted")
        return ResponseEntity.ok().body(service.addSubmittedTimeEntryToTask(taskId, request))
    }
}


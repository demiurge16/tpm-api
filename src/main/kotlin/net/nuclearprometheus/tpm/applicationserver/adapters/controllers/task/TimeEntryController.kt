package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.task

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.TimeEntryApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.requests.ListTimeEntries
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.requests.UpdateTimeEntry
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TimeEntry
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TimeEntryStatusResponse
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/time-entry")
class TimeEntryController(private val service: TimeEntryApplicationService) {

    private val logger = loggerFor(TimeEntryController::class.java)

    @GetMapping("")
    fun getTimeEntries(query: ListTimeEntries): ResponseEntity<Page<TimeEntry>> {
        logger.info("GET /api/v1/time-entry")
        return ResponseEntity.ok().body(service.getTimeEntries(query))
    }

    @GetMapping("/{timeEntryId}")
    fun getTimeEntry(@PathVariable(name = "timeEntryId") timeEntryId: UUID): ResponseEntity<TimeEntry> {
        logger.info("GET /api/v1/time-entry/$timeEntryId")
        return ResponseEntity.ok().body(service.getTimeEntry(timeEntryId))
    }

    @PostMapping("/{timeEntryId}")
    fun updateTimeEntry(@PathVariable(name = "timeEntryId") timeEntryId: UUID, @RequestBody request: UpdateTimeEntry): ResponseEntity<TimeEntry> {
        logger.info("POST /api/v1/time-entry/$timeEntryId")
        return ResponseEntity.ok().body(service.updateTimeEntry(timeEntryId, request))
    }

    @DeleteMapping("/{timeEntryId}")
    fun deleteTimeEntry(@PathVariable(name = "timeEntryId") timeEntryId: UUID): ResponseEntity<Void> {
        logger.info("DELETE /api/v1/time-entry/$timeEntryId")
        service.deleteTimeEntry(timeEntryId)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping("/{timeEntryId}/submit")
    fun submitTimeEntry(@PathVariable(name = "timeEntryId") timeEntryId: UUID): ResponseEntity<TimeEntryStatusResponse> {
        logger.info("PATCH /api/v1/time-entry/$timeEntryId/submit")
        return ResponseEntity.ok().body(service.submitTimeEntry(timeEntryId))
    }

    @PatchMapping("/{timeEntryId}/approve")
    fun approveTimeEntry(@PathVariable(name = "timeEntryId") timeEntryId: UUID): ResponseEntity<TimeEntryStatusResponse> {
        logger.info("PATCH /api/v1/time-entry/$timeEntryId/approve")
        return ResponseEntity.ok().body(service.approveTimeEntry(timeEntryId))
    }

    @PatchMapping("/{timeEntryId}/reject")
    fun rejectTimeEntry(@PathVariable(name = "timeEntryId") timeEntryId: UUID): ResponseEntity<TimeEntryStatusResponse> {
        logger.info("PATCH /api/v1/time-entry/$timeEntryId/reject")
        return ResponseEntity.ok().body(service.rejectTimeEntry(timeEntryId))
    }
}
package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.task

import com.opencsv.bean.StatefulBeanToCsvBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.TimeEntryApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.requests.ListTimeEntries
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.requests.UpdateTimeEntry
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TimeEntry
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TimeEntryStatusResponse
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.OutputStreamWriter
import java.io.PipedInputStream
import java.io.PipedOutputStream
import java.time.LocalDate
import java.util.*
import kotlin.concurrent.thread

@RestController
@RequestMapping("/api/v1/time-entry")
class TimeEntryController(private val service: TimeEntryApplicationService) {

    private val logger = loggerFor(TimeEntryController::class.java)

    @GetMapping("")
    fun getTimeEntries(query: ListTimeEntries): ResponseEntity<Page<TimeEntry>> {
        logger.info("GET /api/v1/time-entry")
        return ResponseEntity.ok().body(service.getTimeEntries(query))
    }

    @GetMapping("/export", produces = ["text/csv"])
    fun export(query: ListTimeEntries) = with(logger) {
        info("GET /api/v1/time-entry/export")

        val files = service.getTimeEntries(query)

        val output = PipedOutputStream()
        val input = PipedInputStream(output)

        thread {
            val writer = OutputStreamWriter(output)
            val beanToCsv = StatefulBeanToCsvBuilder<TimeEntry>(writer).build()
            beanToCsv.write(files.items)
            writer.flush()
            writer.close()
            output.close()
        }

        val name = "tasks-${LocalDate.now()}.csv"
        val resource = InputStreamResource(input)

        ResponseEntity.ok()
            .headers(
                HttpHeaders().apply {
                    add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=$name")
                    add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
                    add(HttpHeaders.PRAGMA, "no-cache")
                    add(HttpHeaders.EXPIRES, "0")
                }
            )

            .contentType(MediaType.parseMediaType("text/csv"))
            .body(resource)
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


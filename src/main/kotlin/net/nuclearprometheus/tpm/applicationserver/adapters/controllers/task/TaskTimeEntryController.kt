package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.task

import com.opencsv.bean.StatefulBeanToCsvBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.TaskTimeEntryApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.requests.CreateTimeEntry
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.requests.ListTimeEntries
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TimeEntry
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.OutputStreamWriter
import java.io.PipedInputStream
import java.io.PipedOutputStream
import java.time.LocalDate
import java.util.UUID
import kotlin.concurrent.thread

@RestController
@RequestMapping("/api/v1/task/{taskId}/time-entry")
class TaskTimeEntryController(private val service: TaskTimeEntryApplicationService) {

    private val logger = loggerFor(TaskTimeEntryController::class.java)

    @GetMapping("")
    fun getTimeEntries(@PathVariable(name = "taskId") taskId: UUID, query: ListTimeEntries): ResponseEntity<Page<TimeEntry>> {
        logger.info("GET /api/v1/task/$taskId/time-entry")
        return ResponseEntity.ok().body(service.getTimeEntriesForTask(taskId, query))
    }

    @GetMapping("/export", produces = ["text/csv"])
    fun export(@PathVariable(name = "taskId") taskId: UUID, query: ListTimeEntries) = with(logger) {
        info("GET /api/v1/project/export")

        val files = service.getTimeEntriesForTask(taskId, query)

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


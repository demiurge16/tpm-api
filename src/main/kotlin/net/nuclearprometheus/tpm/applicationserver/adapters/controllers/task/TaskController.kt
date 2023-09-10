package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.task

import com.opencsv.bean.StatefulBeanToCsvBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.TaskApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.requests.TaskRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TaskResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.ErrorResponse
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
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
@RequestMapping("/api/v1/task")
class TaskController(private val service: TaskApplicationService) {

    private val logger = loggerFor(TaskController::class.java)

    @GetMapping("")
    fun getTasks(query: TaskRequest.List) = with(logger) {
        info("GET /api/v1/task")
        ResponseEntity.ok().body(service.getTasks(query))
    }

    @GetMapping("/export", produces = ["text/csv"])
    fun export(query: TaskRequest.List) = with(logger) {
        info("GET /api/v1/project/export")

        val files = service.getTasks(query)

        val output = PipedOutputStream()
        val input = PipedInputStream(output)

        thread {
            val writer = OutputStreamWriter(output)
            val beanToCsv = StatefulBeanToCsvBuilder<TaskResponse.Task>(writer).build()
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

    @GetMapping("/{taskId}")
    fun getTask(@PathVariable(name = "taskId") taskId: UUID) = with(logger) {
        info("GET /api/v1/task/$taskId")
        ResponseEntity.ok().body(service.getTask(taskId))
    }

    @PutMapping("/{taskId}")
    fun updateTask(@PathVariable(name = "taskId") taskId: UUID, @RequestBody request: TaskRequest.Update) =
        with(logger) {
            info("PUT /api/v1/task/$taskId")
            ResponseEntity.ok().body(service.updateTask(taskId, request))
        }

    @PatchMapping("/{taskId}/move-start")
    fun moveStart(@PathVariable(name = "taskId") taskId: UUID, @RequestBody request: TaskRequest.MoveStart) =
        with(logger) {
            info("PATCH /api/v1/task/$taskId/move-start")
            ResponseEntity.ok().body(service.moveStart(taskId, request))
        }

    @PatchMapping("/{taskId}/move-deadline")
    fun moveDeadline(@PathVariable(name = "taskId") taskId: UUID, @RequestBody request: TaskRequest.MoveDeadline) =
        with(logger) {
            info("PATCH /api/v1/task/$taskId/move-deadline")
            ResponseEntity.ok().body(service.moveDeadline(taskId, request))
        }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: NotFoundException) = with(logger) {
        warn("NotFoundException: ${e.message}")
        ResponseEntity.notFound().build<Unit>()
    }

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(e: IllegalStateException) = with(logger) {
        warn("IllegalStateException: ${e.message}")
        ResponseEntity.internalServerError().body(
            ErrorResponse(e.message ?: "Illegal state")
        )
    }
}


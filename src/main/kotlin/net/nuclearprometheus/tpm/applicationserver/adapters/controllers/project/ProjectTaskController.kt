package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.project

import com.opencsv.bean.StatefulBeanToCsvBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.ProjectTaskApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.requests.ListTasks
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.Task as TaskResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.responses.ErrorResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.CreateTask
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
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
@RequestMapping("/api/v1/project/{projectId}/task")
class ProjectTaskController(
    private val service: ProjectTaskApplicationService
) {

    private val logger = loggerFor(ProjectTaskController::class.java)

    @GetMapping("")
    fun getTasks(@PathVariable(name = "projectId") projectId: UUID, query: ListTasks) = with(logger) {
        info("GET /api/v1/project/$projectId/task")

        ResponseEntity.ok().body(service.getTasksForProject(projectId, query))
    }

    @GetMapping("/export", produces = ["text/csv"])
    fun export(@PathVariable(name = "projectId") projectId: UUID, query: ListTasks) = with(logger) {
        info("GET /api/v1/project/$projectId/task/export")

        val files = service.getTasksForProject(projectId, query)

        val output = PipedOutputStream()
        val input = PipedInputStream(output)

        thread {
            val writer = OutputStreamWriter(output)
            val beanToCsv = StatefulBeanToCsvBuilder<TaskResponse>(writer).build()
            beanToCsv.write(files.items)
            writer.flush()
            writer.close()
            output.close()
        }

        val name = "project-$projectId-tasks-${LocalDate.now()}.csv"
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
    fun createTask(@PathVariable(name = "projectId") projectId: UUID, @RequestBody request: CreateTask) =
        with(logger) {
            info("POST /api/v1/project/$projectId/task")

            ResponseEntity.ok().body(service.createTask(projectId, request))
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


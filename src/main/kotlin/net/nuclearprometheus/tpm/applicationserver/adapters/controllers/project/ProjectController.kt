package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.project

import com.opencsv.bean.StatefulBeanToCsvBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.ProjectApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests.*
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.Project
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectDeadlineMoved
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectStartMoved
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.Project as ProjectResponse
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
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
@RequestMapping("/api/v1/project")
class ProjectController(private val service: ProjectApplicationService) {

    private val logger = loggerFor(ProjectController::class.java)

    @GetMapping("")
    fun getProjects(query: ListProjects): ResponseEntity<Page<Project>> {
        logger.info("GET /api/v1/project")
        return ResponseEntity.ok().body(service.getProjects(query))
    }

    @GetMapping("/export", produces = ["text/csv"])
    fun export(query: ListProjects): ResponseEntity<InputStreamResource> = with(logger) {
        info("GET /api/v1/project/export")

        val files = service.getProjects(query)

        val output = PipedOutputStream()
        val input = PipedInputStream(output)

        thread {
            val writer = OutputStreamWriter(output)
            val beanToCsv = StatefulBeanToCsvBuilder<ProjectResponse>(writer).build()
            beanToCsv.write(files.items)
            writer.flush()
            writer.close()
            output.close()
        }

        val name = "projects-${LocalDate.now()}.csv"
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

    @GetMapping("/{projectId}")
    fun getProject(@PathVariable(name = "projectId") id: UUID): ResponseEntity<Project> {
        logger.info("GET /api/v1/project/$id")
        return ResponseEntity.ok().body(service.getProject(id))
    }

    @PostMapping("")
    fun createProject(@RequestBody request: CreateProject): ResponseEntity<Project> {
        logger.info("POST /api/v1/project")
        return ResponseEntity.ok().body(service.createProject(request))
    }

    @PutMapping("/{projectId}")
    fun updateProject(
        @PathVariable(name = "projectId") id: UUID, @RequestBody request: UpdateProject
    ): ResponseEntity<Project> {
        logger.info("PUT /api/v1/project/$id")
        return ResponseEntity.ok().body(service.updateProject(id, request))
    }

    @PatchMapping("/{projectId}/move-start")
    fun moveStart(
        @PathVariable(name = "projectId") id: UUID, @RequestBody request: MoveProjectStart
    ): ResponseEntity<ProjectStartMoved> {
        logger.info("PATCH /api/v1/project/$id/move-start")
        return ResponseEntity.ok().body(service.moveProjectStart(id, request))
    }

    @PatchMapping("/{projectId}/move-deadline")
    fun moveDeadline(
        @PathVariable(name = "projectId") id: UUID, @RequestBody request: MoveProjectDeadline
    ): ResponseEntity<ProjectDeadlineMoved> {
        logger.info("PATCH /api/v1/project/$id/move-deadline")
        return ResponseEntity.ok().body(service.moveProjectDeadline(id, request))
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: NotFoundException): ResponseEntity<Unit> {
        logger.warn("NotFoundException: ${e.message}")
        return ResponseEntity.notFound().build()
    }
}

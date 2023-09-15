package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.project

import com.opencsv.bean.StatefulBeanToCsvBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.file.requests.FileRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.file.responses.FileResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.ProjectFileApplicationService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.OutputStreamWriter
import java.io.PipedInputStream
import java.io.PipedOutputStream
import java.time.LocalDate
import java.util.*
import kotlin.concurrent.thread

@RestController
@RequestMapping("/api/v1/project/{projectId}/file")
class ProjectFileController(
    private val service: ProjectFileApplicationService
) {

    private val logger = loggerFor(ProjectFileController::class.java)

    @GetMapping("")
    fun getFiles(@PathVariable(name = "projectId") projectId: UUID, query: FileRequest.List) = with(logger) {
        info("GET /api/v1/project/$projectId/file")

        ResponseEntity.ok().body(service.getFilesForProject(projectId, query))
    }

    @GetMapping("/export", produces = ["text/csv"])
    fun export(@PathVariable(name = "projectId") projectId: UUID, query: FileRequest.List) = with(logger) {
        info("GET /api/v1/project/$projectId/file/export")

        val files = service.getFilesForProject(projectId, query)

        val output = PipedOutputStream()
        val input = PipedInputStream(output)

        thread {
            val writer = OutputStreamWriter(output)
            val beanToCsv = StatefulBeanToCsvBuilder<FileResponse.File>(writer).build()
            beanToCsv.write(files.items)
            writer.flush()
            writer.close()
            output.close()
        }

        val name = "project-$projectId-files-${LocalDate.now()}.csv"
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

    @PostMapping("", consumes = ["multipart/form-data"])
    fun addFile(@PathVariable(name = "projectId") projectId: UUID, @RequestParam(name = "file") request: MultipartFile) =
        with(logger) {
            info("POST /api/v1/project/$projectId/file")

            ResponseEntity.ok().body(service.addFile(projectId, request))
        }
}


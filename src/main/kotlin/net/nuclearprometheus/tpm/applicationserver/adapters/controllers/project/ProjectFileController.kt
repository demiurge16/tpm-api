package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.project

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.ProjectFileApplicationService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/api/v1/project/{projectId}/file")
class ProjectFileController(
    private val service: ProjectFileApplicationService
) {

    private val logger = loggerFor(ProjectFileController::class.java)

    @GetMapping("")
    fun getFiles(@PathVariable(name = "projectId") projectId: UUID) = with(logger) {
        info("GET /api/v1/project/$projectId/file")

        service.getFilesForProject(projectId)
    }

    @PostMapping("")
    fun addFile(@PathVariable(name = "projectId") projectId: UUID, @RequestParam(name = "file") request: MultipartFile) =
        with(logger) {
            info("POST /api/v1/project/$projectId/file")
            info("request: $request")

            service.addFile(projectId, request)
        }
}


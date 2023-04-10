package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.project

import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/project/{projectId}/file")
class ProjectFileController {

    @GetMapping("")
    fun getFiles(@PathVariable(name = "projectId") projectId: UUID) {
        TODO()
    }

    @PostMapping("")
    fun createFile(@PathVariable(name = "projectId") projectId: UUID, @RequestBody request: Any) {
        TODO()
    }
}

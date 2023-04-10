package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.project

import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/project/{projectId}/note")
class ProjectNoteController {

    @GetMapping("")
    fun getNotes(@PathVariable(name = "projectId") projectId: UUID) {
        TODO()
    }

    @PostMapping("")
    fun createNote(@PathVariable(name = "projectId") projectId: UUID, @RequestBody request: Any) {
        TODO()
    }
}
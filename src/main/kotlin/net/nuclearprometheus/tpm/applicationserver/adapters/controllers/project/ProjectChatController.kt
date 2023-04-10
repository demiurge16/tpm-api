package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.project

import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/project/{projectId}/chat")
class ProjectChatController {

    @GetMapping("")
    fun getChats(@PathVariable(name = "projectId") projectId: UUID) {
        TODO()
    }

    @PostMapping("")
    fun createChat(@PathVariable(name = "projectId") projectId: UUID, @RequestBody request: Any) {
        TODO()
    }
}

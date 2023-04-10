package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.note

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/note")
class NoteController {

    @GetMapping("")
    fun getNotes() {
        TODO()
    }

    @GetMapping("/{noteId}")
    fun getNote(@PathVariable(name = "noteId") noteId: UUID) {
        TODO()
    }

    @DeleteMapping("/{noteId}")
    fun deleteNote(@PathVariable(name = "noteId") noteId: UUID) {
        TODO()
    }
}
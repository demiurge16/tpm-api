package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.note

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.note.NoteApplicationService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/note")
class NoteController(private val service: NoteApplicationService) {

    private val logger = loggerFor(NoteController::class.java)

    @GetMapping("")
    fun getNotes() = with(logger) {
        info("GET /api/v1/note")

        ResponseEntity.ok().body(service.getNotes())
    }

    @GetMapping("/{noteId}")
    fun getNote(@PathVariable(name = "noteId") noteId: UUID) = with(logger) {
        info("GET /api/v1/note/$noteId")

        ResponseEntity.ok().body(service.getNote(noteId))
    }

    @DeleteMapping("/{noteId}")
    fun deleteNote(@PathVariable(name = "noteId") noteId: UUID) = with(logger) {
        info("DELETE /api/v1/note/$noteId")

        service.deleteNote(noteId)
        ResponseEntity.noContent().build<Void>()
    }
}

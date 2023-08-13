package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.thread

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.ThreadStatusApplicationService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/v1/thread")
class ThreadStatusController(private val service: ThreadStatusApplicationService) {

    private val logger = loggerFor(ThreadStatusController::class.java)

    @PatchMapping("/{id}/activate")
    fun activateThread(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/thread/$id/activate")

        ResponseEntity.ok().body(service.activate(id))
    }

    @PatchMapping("/{id}/freeze")
    fun freezeThread(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/thread/$id/freeze")

        ResponseEntity.ok().body(service.freeze(id))
    }

    @PatchMapping("/{id}/close")
    fun closeThread(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/thread/$id/close")

        ResponseEntity.ok().body(service.close(id))
    }

    @PatchMapping("/{id}/archive")
    fun archiveThread(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/thread/$id/archive")

        ResponseEntity.ok().body(service.archive(id))
    }

    @PatchMapping("/{id}/delete")
    fun deleteThread(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/thread/$id/delete")

        ResponseEntity.ok().body(service.delete(id))
    }
}

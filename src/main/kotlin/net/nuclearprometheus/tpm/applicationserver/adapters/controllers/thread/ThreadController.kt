package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.thread

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.ThreadApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.requests.ThreadRequest
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/thread")
class ThreadController(private val service: ThreadApplicationService) {

    private val logger = loggerFor(ThreadController::class.java)

    @GetMapping("")
    fun getThreads() = with(logger) {
        info("GET /api/v1/thread")

        ResponseEntity.ok().body(service.getThreads())
    }

    @GetMapping("/{id}")
    fun getThread(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("GET /api/v1/thread/$id")

        ResponseEntity.ok().body(service.getThread(id))
    }

    @PutMapping("/{id}")
    fun updateThread(@PathVariable(name = "id") id: UUID, @RequestBody request: ThreadRequest.Update) = with(logger) {
        info("PUT /api/v1/thread/$id")

        ResponseEntity.ok().body(service.updateThread(id, request))
    }

    @PatchMapping("/{id}/like")
    fun like(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/thread/$id/like")

        ResponseEntity.ok().body(service.addLike(id))
    }

    @PatchMapping("/{id}/dislike")
    fun dislike(@PathVariable(name = "id") id: UUID) = with(logger) {
        info("PATCH /api/v1/thread/$id/dislike")

        ResponseEntity.ok().body(service.addDislike(id))
    }
}


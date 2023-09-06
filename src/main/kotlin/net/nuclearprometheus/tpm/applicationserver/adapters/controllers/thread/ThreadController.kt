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
    fun getThreads(query: ThreadRequest.List) = with(logger) {
        info("GET /api/v1/thread")
        ResponseEntity.ok().body(service.getThreads(query))
    }

    @GetMapping("/{threadId}")
    fun getThread(@PathVariable(name = "threadId") id: UUID) = with(logger) {
        info("GET /api/v1/thread/$id")
        ResponseEntity.ok().body(service.getThread(id))
    }

    @PutMapping("/{threadId}")
    fun updateThread(@PathVariable(name = "threadId") id: UUID, @RequestBody request: ThreadRequest.Update) = with(logger) {
        info("PUT /api/v1/thread/$id")
        ResponseEntity.ok().body(service.updateThread(id, request))
    }

    @PatchMapping("/{threadId}/like")
    fun like(@PathVariable(name = "threadId") id: UUID) = with(logger) {
        info("PATCH /api/v1/thread/$id/like")
        ResponseEntity.ok().body(service.addLike(id))
    }

    @PatchMapping("/{threadId}/unlike")
    fun unlike(@PathVariable(name = "threadId") id: UUID) = with(logger) {
        info("PATCH /api/v1/thread/$id/unlike")
        ResponseEntity.ok().body(service.removeLike(id))
    }

    @PatchMapping("/{threadId}/dislike")
    fun dislike(@PathVariable(name = "threadId") id: UUID) = with(logger) {
        info("PATCH /api/v1/thread/$id/dislike")
        ResponseEntity.ok().body(service.addDislike(id))
    }

    @PatchMapping("/{threadId}/undislike")
    fun undislike(@PathVariable(name = "threadId") id: UUID) = with(logger) {
        info("PATCH /api/v1/thread/$id/undislike")
        ResponseEntity.ok().body(service.removeDislike(id))
    }
}


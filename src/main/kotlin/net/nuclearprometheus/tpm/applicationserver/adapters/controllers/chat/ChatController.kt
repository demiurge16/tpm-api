package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.chat

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.ChatApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.requests.ChatRequest
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/chat")
class ChatController(private val service: ChatApplicationService) {

    private val logger = loggerFor(ChatController::class.java)

    @GetMapping("")
    fun getChats() = with(logger) {
        info("GET /api/v1/chat")

        ResponseEntity.ok().body(service.getChats())
    }

    @GetMapping("/{chatId}")
    fun getChat(@PathVariable(name = "chatId") chatId: UUID) = with(logger) {
        info("GET /api/v1/chat/$chatId")

        ResponseEntity.ok().body(service.getChat(chatId))
    }

    @PutMapping("/{chatId}")
    fun updateChat(@PathVariable(name = "chatId") chatId: UUID, @RequestBody request: ChatRequest.Update) =
        with(logger) {
            info("PUT /api/v1/chat/$chatId")

            ResponseEntity.ok().body(service.updateChat(chatId, request))
        }

    @PatchMapping("/{chatId}/activate")
    fun activateChat(@PathVariable(name = "chatId") chatId: UUID) = with(logger) {
        info("PATCH /api/v1/chat/$chatId/activate")

        ResponseEntity.ok().body(service.activateChat(chatId))
    }

    @PatchMapping("/{chatId}/freeze")
    fun freezeChat(@PathVariable(name = "chatId") chatId: UUID) = with(logger) {
        info("PATCH /api/v1/chat/$chatId/freeze")

        ResponseEntity.ok().body(service.freezeChat(chatId))
    }

    @PatchMapping("/{chatId}/archive")
    fun archiveChat(@PathVariable(name = "chatId") chatId: UUID) = with(logger) {
        info("PATCH /api/v1/chat/$chatId/archive")

        ResponseEntity.ok().body(service.archiveChat(chatId))
    }

    @PatchMapping("/{chatId}/transfer-ownership")
    fun transferOwnership(
        @PathVariable(name = "chatId") chatId: UUID,
        @RequestBody request: ChatRequest.TransferOwnership
    ) = with(logger) {
        info("PATCH /api/v1/chat/$chatId/transfer-ownership")

        ResponseEntity.ok().body(service.transferOwnership(chatId, request))
    }

    @PatchMapping("/{chatId}/add-participant")
    fun addParticipant(@PathVariable(name = "chatId") chatId: UUID, @RequestBody request: ChatRequest.AddParticipant) =
        with(logger) {
            info("PATCH /api/v1/chat/$chatId/add-participant")

            ResponseEntity.ok().body(service.addParticipant(chatId, request))
        }

    @PatchMapping("/{chatId}/remove-participant")
    fun removeParticipant(
        @PathVariable(name = "chatId") chatId: UUID,
        @RequestBody request: ChatRequest.RemoveParticipant
    ) = with(logger) {
        info("PATCH /api/v1/chat/$chatId/remove-participant")

        service.removeParticipant(chatId, request)
        ResponseEntity.noContent().build<Void>()
    }
}


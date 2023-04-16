package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.chat

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.ChatMessageApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.requests.ChatMessageRequest
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/chat/{chatId}/message")
class ChatMessageController(private val service: ChatMessageApplicationService) {

    private val logger = loggerFor(ChatMessageController::class.java)

    @GetMapping("")
    fun getMessages(@PathVariable(name = "chatId") chatId: UUID) = with(logger) {
        info("GET /api/v1/chat/$chatId/message")

        ResponseEntity.ok().body(service.getMessages(chatId))
    }

    @PostMapping("")
    fun createMessage(@PathVariable(name = "chatId") chatId: UUID, @RequestBody request: ChatMessageRequest.Create) =
        with(logger) {
            info("POST /api/v1/chat/$chatId/message")

            ResponseEntity.ok().body(service.createMessage(chatId, request))
        }
}


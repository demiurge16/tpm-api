package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.chat

import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/chat/{chatId}/message")
class ChatMessageController {

    @GetMapping("")
    fun getMessages(@PathVariable(name = "chatId") chatId: UUID) {
        TODO()
    }

    @PostMapping("")
    fun createMessage(@PathVariable(name = "chatId") chatId: UUID) {
        TODO()
    }
}

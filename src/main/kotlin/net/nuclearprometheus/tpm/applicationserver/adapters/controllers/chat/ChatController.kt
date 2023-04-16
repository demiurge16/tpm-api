package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.chat

import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/chat")
class ChatController() {

    private val logger = loggerFor(this::class.java)

    @GetMapping("")
    fun getChats() {
        TODO()
    }

    @GetMapping("/{chatId}")
    fun getChat(@PathVariable(name = "chatId") chatId: UUID) {
        TODO()
    }

    @PutMapping("/{chatId}")
    fun updateChat(@PathVariable(name = "chatId") chatId: UUID) {
        TODO()
    }

    @PatchMapping("/{chatId}/activate")
    fun activateChat(@PathVariable(name = "chatId") chatId: UUID) {
        TODO()
    }

    @PatchMapping("/{chatId}/freeze")
    fun freezeChat(@PathVariable(name = "chatId") chatId: UUID) {
        TODO()
    }

    @PatchMapping("/{chatId}/archive")
    fun archiveChat(@PathVariable(name = "chatId") chatId: UUID) {
        TODO()
    }

    @PatchMapping("/{chatId}/transfer-ownership")
    fun transferOwnership(@PathVariable(name = "chatId") chatId: UUID) {
        TODO()
    }

    @PatchMapping("/{chatId}/add-participant")
    fun addParticipant(@PathVariable(name = "chatId") chatId: UUID) {
        TODO()
    }

    @PatchMapping("/{chatId}/remove-participant")
    fun removeParticipant(@PathVariable(name = "chatId") chatId: UUID) {
        TODO()
    }
}

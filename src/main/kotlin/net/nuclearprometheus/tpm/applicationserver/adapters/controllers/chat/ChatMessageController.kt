package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.chat

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.ChatMessageApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.requests.ChatMessageRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.responses.ChatMessageResponse
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@Tag(name = "Chat messages", description = "Chat message API")
@RestController
@RequestMapping("/api/v1/chat/{chatId}/message")
class ChatMessageController(private val service: ChatMessageApplicationService) {

    private val logger = loggerFor(ChatMessageController::class.java)

    @Operation(summary = "Get all messages in a chat", method = "GET", description = "Get all messages in a chat", tags = ["Chat messages"])
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = ChatMessageResponse.Page::class)
                )
            ]
        )
    ])
    @GetMapping("")
    fun getMessages(
        @Parameter(description = "Chat Id", required = true, schema = Schema(type = "string", format = "uuid"))
        @PathVariable(name = "chatId") chatId: UUID
    ) = with(logger) {
        info("GET /api/v1/chat/$chatId/message")

        ResponseEntity.ok().body(service.getMessages(chatId))
    }

    @Operation(summary = "Get a message by Id", method = "GET", description = "Get a message by Id", tags = ["Chat messages"])
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "OK",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ChatMessageResponse.View::class)
                )
            ]
        )
    ])
    @PostMapping("")
    fun createMessage(
        @Parameter(description = "Chat Id", required = true, schema = Schema(type = "string", format = "uuid"))
        @PathVariable(name = "chatId") chatId: UUID,
        @Parameter(description = "Message", required = true, schema = Schema(implementation = ChatMessageRequest.Create::class))
        @RequestBody request: ChatMessageRequest.Create
    ) =
        with(logger) {
            info("POST /api/v1/chat/$chatId/message")

            ResponseEntity.ok().body(service.createMessage(chatId, request))
        }
}


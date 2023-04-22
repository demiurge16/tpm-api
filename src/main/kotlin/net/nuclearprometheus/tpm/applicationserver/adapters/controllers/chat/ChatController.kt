package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.chat

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.ChatApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.requests.ChatRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.chat.responses.ChatResponse
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@Tag(name = "Chat", description = "Chat API")
@RestController
@RequestMapping("/api/v1/chat")
class ChatController(private val service: ChatApplicationService) {

    private val logger = loggerFor(ChatController::class.java)

    @Operation(summary = "Get all chats", method = "GET", description = "Get all chats", tags = ["Chat"])
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "OK",
            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = ChatResponse.Page::class)
                )
            ]
        )
    ])
    @GetMapping("")
    fun getChats() = with(logger) {
        info("GET /api/v1/chat")

        ResponseEntity.ok().body(service.getChats())
    }

    @Operation(summary = "Get a chat by Id", method = "GET", description = "Get a chat by Id", tags = ["Chat"])
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "OK",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ChatResponse.Chat::class)
                )
            ]
        )
    ])
    @GetMapping("/{chatId}")
    fun getChat(
        @Parameter(description = "Chat Id", required = true, schema = Schema(type = "string", format = "uuid"))
        @PathVariable(name = "chatId") chatId: UUID
    ) = with(logger) {
        info("GET /api/v1/chat/$chatId")

        ResponseEntity.ok().body(service.getChat(chatId))
    }

    @Operation(summary = "Update chat", method = "PUT", description = "Update an existing chat", tags = ["Chat"])
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "OK",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ChatResponse.Chat::class)
                )
            ]
        )
    ])
    @PutMapping("/{chatId}")
    fun updateChat(
        @Parameter(description = "Chat Id", required = true, schema = Schema(type = "string", format = "uuid"))
        @PathVariable(name = "chatId") chatId: UUID,
        @Parameter(description = "Chat update request", required = true, schema = Schema(implementation = ChatRequest.Update::class))
        @RequestBody request: ChatRequest.Update
    ) =
        with(logger) {
            info("PUT /api/v1/chat/$chatId")

            ResponseEntity.ok().body(service.updateChat(chatId, request))
        }

    @Operation(summary = "Activate chat", method = "PATCH", description = "Change status of a chat to Active", tags = ["Chat"])
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "OK",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ChatResponse.ChatStatus::class)
                )
            ]
        )
    ])
    @PatchMapping("/{chatId}/activate")
    fun activateChat(
        @Parameter(description = "Chat Id", required = true, schema = Schema(type = "string", format = "uuid"))
        @PathVariable(name = "chatId") chatId: UUID
    ) = with(logger) {
        info("PATCH /api/v1/chat/$chatId/activate")

        ResponseEntity.ok().body(service.activateChat(chatId))
    }

    @Operation(summary = "Freeze chat", method = "PATCH", description = "Change status of a chat to Frozen", tags = ["Chat"])
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "OK",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ChatResponse.ChatStatus::class)
                )
            ]
        )
    ])
    @PatchMapping("/{chatId}/freeze")
    fun freezeChat(
        @Parameter(description = "Chat Id", required = true, schema = Schema(type = "string", format = "uuid"))
        @PathVariable(name = "chatId") chatId: UUID
    ) = with(logger) {
        info("PATCH /api/v1/chat/$chatId/freeze")

        ResponseEntity.ok().body(service.freezeChat(chatId))
    }

    @Operation(summary = "Archive chat", method = "PATCH", description = "Change status of a chat to Archived", tags = ["Chat"])
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "OK",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ChatResponse.ChatStatus::class)
                )
            ]
        )
    ])
    @PatchMapping("/{chatId}/archive")
    fun archiveChat(
        @Parameter(description = "Chat Id", required = true, schema = Schema(type = "string", format = "uuid"))
        @PathVariable(name = "chatId") chatId: UUID
    ) = with(logger) {
        info("PATCH /api/v1/chat/$chatId/archive")

        ResponseEntity.ok().body(service.archiveChat(chatId))
    }

    @Operation(summary = "Transfer ownership", method = "PATCH", description = "Transfer ownership to another chat member", tags = ["Chat"])
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "OK",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ChatResponse.ChatMember::class)
                )
            ]
        )
    ])
    @PatchMapping("/{chatId}/transfer-ownership")
    fun transferOwnership(
        @Parameter(description = "Chat Id", required = true, schema = Schema(type = "string", format = "uuid"))
        @PathVariable(name = "chatId") chatId: UUID,
        @Parameter(description = "Transfer ownership request", required = true, schema = Schema(implementation = ChatRequest.TransferOwnership::class))
        @RequestBody request: ChatRequest.TransferOwnership
    ) = with(logger) {
        info("PATCH /api/v1/chat/$chatId/transfer-ownership")

        ResponseEntity.ok().body(service.transferOwnership(chatId, request))
    }

    @Operation(summary = "Add participant", method = "PATCH", description = "Add a new participant to a chat", tags = ["Chat"])
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "OK",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ChatResponse.ChatMember::class)
                )
            ]
        )
    ])
    @PatchMapping("/{chatId}/add-participant")
    fun addParticipant(
        @Parameter(description = "Chat Id", required = true, schema = Schema(type = "string", format = "uuid"))
        @PathVariable(name = "chatId") chatId: UUID,
        @Parameter(description = "Add participant request", required = true, schema = Schema(implementation = ChatRequest.AddParticipant::class))
        @RequestBody request: ChatRequest.AddParticipant
    ) =
        with(logger) {
            info("PATCH /api/v1/chat/$chatId/add-participant")

            ResponseEntity.ok().body(service.addParticipant(chatId, request))
        }

    @Operation(summary = "Remove participant", method = "PATCH", description = "Remove a participant from a chat", tags = ["Chat"])
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "204",
            description = "No Content"
        )
    ])
    @PatchMapping("/{chatId}/remove-participant")
    fun removeParticipant(
        @Parameter(description = "Chat Id", required = true, schema = Schema(type = "string", format = "uuid"))
        @PathVariable(name = "chatId") chatId: UUID,
        @Parameter(description = "Remove participant request", required = true, schema = Schema(implementation = ChatRequest.RemoveParticipant::class))
        @RequestBody request: ChatRequest.RemoveParticipant
    ) = with(logger) {
        info("PATCH /api/v1/chat/$chatId/remove-participant")

        service.removeParticipant(chatId, request)
        ResponseEntity.noContent().build<Void>()
    }
}


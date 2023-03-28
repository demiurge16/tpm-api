package net.nuclearprometheus.tpm.applicationserver.domain.model.chat

enum class ChatStatus(val title: String, val description: String) {
    ACTIVE("Active", "Chat is active"),
    FREEZE("Freeze", "Chat is frozen"),
    ARCHIVE("Archive", "Chat is archived")
}
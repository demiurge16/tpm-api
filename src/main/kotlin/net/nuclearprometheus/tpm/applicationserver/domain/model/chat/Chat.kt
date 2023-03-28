package net.nuclearprometheus.tpm.applicationserver.domain.model.chat

class Chat(
    val id: ChatId = ChatId(),
    val messages: MutableList<Message> = mutableListOf()
) {
    fun addMessage(message: Message) {
        messages.add(message)
    }
}

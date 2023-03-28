package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.chat

import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.Message
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.MessageId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository

interface MessageRepository : BaseRepository<Message, MessageId>

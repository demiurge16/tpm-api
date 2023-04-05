package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.chat.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.chat.entities.MessageDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.chat.repositories.MessageJpaRepository
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.teammember.adapters.TeamMemberRepositoryImpl.Mappers.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.ChatId
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.Message
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.MessageId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.chat.MessageRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import org.springframework.stereotype.Repository

@Repository
class MessageRepositoryImpl(
    private val jpaRepository: MessageJpaRepository,
    private val teamMemberRepository: TeamMemberRepository
): MessageRepository {

    override fun getAll() = jpaRepository.findAll().map { it.toDomain(teamMemberRepository) }
    override fun get(id: MessageId): Message? = jpaRepository.findById(id.value).map { it.toDomain(teamMemberRepository) }.orElse(null)
    override fun get(ids: List<MessageId>) = jpaRepository.findAllById(ids.map { it.value }).map { it.toDomain(teamMemberRepository) }
    override fun create(entity: Message) = jpaRepository.save(entity.toDatabaseModel()).toDomain(teamMemberRepository)
    override fun createAll(entities: List<Message>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain(teamMemberRepository) }
    override fun update(entity: Message) = jpaRepository.save(entity.toDatabaseModel()).toDomain(teamMemberRepository)
    override fun updateAll(entities: List<Message>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain(teamMemberRepository) }
    override fun delete(id: MessageId) = jpaRepository.deleteById(id.value)
    override fun deleteAll(ids: List<MessageId>) = jpaRepository.deleteAllById(ids.map { it.value })
    override fun getAllByChatId(chatId: ChatId) = jpaRepository.findAllByChatId(chatId.value).map { it.toDomain(teamMemberRepository) }

    companion object Mappers {

        fun MessageDatabaseModel.toDomain(teamMemberRepository: TeamMemberRepository) = Message(
            id = MessageId(id),
            message = message,
            timestamp = timestamp,
            author = teamMemberRepository.get(TeamMemberId(author.id))
                ?: throw IllegalArgumentException("Author with id ${author.id} does not exist"),
            chatId = ChatId(chatId)
        )

        fun Message.toDatabaseModel() = MessageDatabaseModel(
            id = id.value,
            message = message,
            timestamp = timestamp,
            author = author.toDatabaseModel(),
            chatId = chatId.value
        )
    }
}

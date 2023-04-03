package net.nuclearprometheus.tpm.applicationserver.adapters.database.chat.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.database.chat.entities.ChatDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.chat.entities.ChatStatusDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.chat.repositories.ChatJpaRepository
import net.nuclearprometheus.tpm.applicationserver.adapters.database.teammember.adapters.TeamMemberRepositoryImpl.Mappers.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.Chat
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.ChatId
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.ChatStatus
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.chat.ChatRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.chat.MessageRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import org.springframework.stereotype.Repository

@Repository
class ChatMessageImpl(
    private val jpaRepository: ChatJpaRepository,
    private val teamMemberRepository: TeamMemberRepository,
    private val messageRepository: MessageRepository
) : ChatRepository {
    override fun getAll() = jpaRepository.findAll().map { it.toDomain(teamMemberRepository, messageRepository) }
    override fun get(id: ChatId): Chat? = jpaRepository.findById(id.value)
        .map { it.toDomain(teamMemberRepository, messageRepository) }
        .orElse(null)
    override fun get(ids: List<ChatId>) = jpaRepository.findAllById(ids.map { it.value })
        .map { it.toDomain(teamMemberRepository, messageRepository) }
    override fun create(entity: Chat) = jpaRepository.save(entity.toDatabaseModel(teamMemberRepository))
        .toDomain(teamMemberRepository, messageRepository)
    override fun createAll(entities: List<Chat>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel(teamMemberRepository) })
        .map { it.toDomain(teamMemberRepository, messageRepository) }
    override fun update(entity: Chat) = jpaRepository.save(entity.toDatabaseModel(teamMemberRepository))
        .toDomain(teamMemberRepository, messageRepository)
    override fun updateAll(entities: List<Chat>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel(teamMemberRepository) })
        .map { it.toDomain(teamMemberRepository, messageRepository) }
    override fun delete(id: ChatId) = jpaRepository.deleteById(id.value)
    override fun deleteAll(ids: List<ChatId>) = jpaRepository.deleteAllById(ids.map { it.value })
    override fun getAllByProjectId(projectId: ProjectId) = jpaRepository.findAllByProjectId(projectId.value)
        .map { it.toDomain(teamMemberRepository, messageRepository) }

    companion object Mappers {
        fun ChatDatabaseModel.toDomain(
            teamMemberRepository: TeamMemberRepository,
            messageRepository: MessageRepository
        ) = Chat(
            id = ChatId(id),
            title = title,
            description = description,
            status = status.toDomain(),
            owner = teamMemberRepository.get(TeamMemberId(owner.id))
                ?: throw IllegalArgumentException("Owner with id ${owner.id} does not exist"),
            projectId = ProjectId(projectId),
            participants = participants.map { teamMemberRepository.get(TeamMemberId(it.id)) }
                .filterNotNull(),
            messages = messageRepository.getAllByChatId(ChatId(id))
        )

        fun Chat.toDatabaseModel(teamMemberRepository: TeamMemberRepository) = ChatDatabaseModel(
            id = id.value,
            title = title,
            description = description,
            status = status.toDatabaseModel(),
            owner = teamMemberRepository.get(owner.id)?.toDatabaseModel()
                ?: throw IllegalArgumentException("Owner with id ${owner.id} does not exist"),
            participants = participants
                .map {
                    teamMemberRepository.get(it.id)?.toDatabaseModel() ?: throw IllegalArgumentException("Participant with id ${it.id} does not exist")
                }
                .toMutableList(),
            projectId = projectId.value
        )

        fun ChatStatus.toDatabaseModel() = when (this) {
            ChatStatus.ACTIVE -> ChatStatusDatabaseModel.ACTIVE
            ChatStatus.FREEZE -> ChatStatusDatabaseModel.FREEZE
            ChatStatus.ARCHIVE -> ChatStatusDatabaseModel.ARCHIVE
        }

        fun ChatStatusDatabaseModel.toDomain() = when (this) {
            ChatStatusDatabaseModel.ACTIVE -> ChatStatus.ACTIVE
            ChatStatusDatabaseModel.FREEZE -> ChatStatus.FREEZE
            ChatStatusDatabaseModel.ARCHIVE -> ChatStatus.ARCHIVE
        }
    }
}
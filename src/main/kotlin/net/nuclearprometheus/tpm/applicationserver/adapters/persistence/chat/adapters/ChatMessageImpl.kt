package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.chat.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.chat.entities.ChatDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.chat.entities.ChatStatusDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.chat.repositories.ChatJpaRepository
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.teammember.adapters.TeamMemberRepositoryImpl.Mappers.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.Chat
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.ChatId
import net.nuclearprometheus.tpm.applicationserver.domain.model.chat.ChatStatus
import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity
import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Id
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository
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
    override fun create(entity: Chat): Chat {
        val chat = jpaRepository.save(entity.toDatabaseModel(teamMemberRepository))
        val messages = messageRepository.getAllByChatId(entity.id)

        return Chat(
            id = ChatId(chat.id),
            title = chat.title,
            description = chat.description,
            status = chat.status.toDomain(),
            owner = teamMemberRepository.get(TeamMemberId(chat.owner.id))
                ?: throw IllegalArgumentException("Owner with id ${chat.owner.id} does not exist"),
            projectId = ProjectId(chat.projectId),
            participants = chat.participants.map { teamMemberRepository.get(TeamMemberId(it.id)) }
                .filterNotNull(),
            messages = messages
        )
    }

    override fun createAll(entities: List<Chat>) = entities.map { create(it) }

    override fun update(entity: Chat): Chat {
        val project = jpaRepository.save(entity.toDatabaseModel(teamMemberRepository))

        fun <T : Entity<TId>, TId : Id<*>> updateAll(
            previousState: List<T>,
            newState: List<T>,
            repository: BaseRepository<T, TId>
        ): List<T> {
            val toDelete = previousState.filter { it !in newState }
            val toCreate = newState.filter { it !in previousState }
            val toUpdate = newState.filter { it in previousState }

            repository.deleteAll(toDelete.map { it.id })

            return repository.createAll(toCreate) + repository.updateAll(toUpdate)
        }

        val messages = updateAll(
            previousState = messageRepository.getAllByChatId(entity.id),
            newState = entity.messages,
            repository = messageRepository
        )

        return Chat(
            id = ChatId(project.id),
            title = project.title,
            description = project.description,
            status = project.status.toDomain(),
            owner = teamMemberRepository.get(TeamMemberId(project.owner.id))
                ?: throw IllegalArgumentException("Owner with id ${project.owner.id} does not exist"),
            projectId = ProjectId(project.projectId),
            participants = project.participants.map { teamMemberRepository.get(TeamMemberId(it.id)) }
                .filterNotNull(),
            messages = messages
        )
    }
    override fun updateAll(entities: List<Chat>) = entities.map { update(it) }

    override fun delete(id: ChatId) {
        messageRepository.deleteAllByChatId(id)
        jpaRepository.deleteById(id.value)
    }

    override fun deleteAll(ids: List<ChatId>) = jpaRepository.deleteAllById(ids.map { it.value })

    override fun getAllByProjectId(projectId: ProjectId) = jpaRepository.findAllByProjectId(projectId.value)
        .map { it.toDomain(teamMemberRepository, messageRepository) }

    override fun deleteAllByProjectId(projectId: ProjectId) = projectId.let {
        messageRepository.deleteAllByProjectId(it)
        jpaRepository.deleteAllByProjectId(it.value)
    }

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
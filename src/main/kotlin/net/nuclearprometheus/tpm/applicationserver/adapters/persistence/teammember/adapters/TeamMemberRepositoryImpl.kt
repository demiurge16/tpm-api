package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.teammember.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.teammember.entities.TeamMemberDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.teammember.entities.TeamMemberRoleDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.teammember.repositories.TeamMemberJpaRepository
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMember
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberRole
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import org.springframework.stereotype.Repository

@Repository
class TeamMemberRepositoryImpl(
    private val repository: TeamMemberJpaRepository,
    private val userRepository: UserRepository
) : TeamMemberRepository {

    override fun getAll() = repository.findAll().map { it.toDomain(userRepository) }
    override fun get(id: TeamMemberId) = repository.findById(id.value).map { it.toDomain(userRepository) }.orElse(null)
    override fun get(ids: List<TeamMemberId>) = repository.findAllById(ids.map { it.value }).map { it.toDomain(userRepository) }
    override fun create(entity: TeamMember) = repository.save(entity.toDatabaseModel()).toDomain(userRepository)
    override fun createAll(entities: List<TeamMember>) = repository.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain(userRepository) }
    override fun update(entity: TeamMember) = repository.save(entity.toDatabaseModel()).toDomain(userRepository)
    override fun updateAll(entities: List<TeamMember>) = repository.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain(userRepository) }
    override fun delete(id: TeamMemberId) = repository.deleteById(id.value)
    override fun deleteAll(ids: List<TeamMemberId>) = repository.deleteAllById(ids.map { it.value })
    override fun getAllByProjectId(projectId: ProjectId) = repository.findAllByProjectId(projectId.value).map { it.toDomain(userRepository) }
    override fun deleteAllByProjectId(projectId: ProjectId) = repository.deleteAllByProjectId(projectId.value)
    override fun getByUserIdAndProjectId(userId: UserId, projectId: ProjectId): TeamMember? =
        repository.findByUserIdAndProjectId(userId.value, projectId.value)?.toDomain(userRepository)

    companion object Mappers {
        fun TeamMemberDatabaseModel.toDomain(userRepository: UserRepository) = TeamMember(
            id = TeamMemberId(id),
            user = userRepository.get(UserId(userId)) ?: throw IllegalStateException("User with id $userId not found"),
            role = role.toDomain(),
            projectId = ProjectId(projectId)
        )

        fun TeamMember.toDatabaseModel() = TeamMemberDatabaseModel(
            id = id.value,
            userId = user.id.value,
            role = role.toDatabaseModel(),
            projectId = projectId.value
        )

        fun TeamMemberRoleDatabaseModel.toDomain() = when (this) {
            TeamMemberRoleDatabaseModel.PROJECT_MANAGER -> TeamMemberRole.PROJECT_MANAGER
            TeamMemberRoleDatabaseModel.TRANSLATOR -> TeamMemberRole.TRANSLATOR
            TeamMemberRoleDatabaseModel.EDITOR -> TeamMemberRole.EDITOR
            TeamMemberRoleDatabaseModel.PROOFREADER -> TeamMemberRole.PROOFREADER
            TeamMemberRoleDatabaseModel.SUBJECT_MATTER_EXPERT -> TeamMemberRole.SUBJECT_MATTER_EXPERT
            TeamMemberRoleDatabaseModel.PUBLISHER -> TeamMemberRole.PUBLISHER
            TeamMemberRoleDatabaseModel.OBSERVER -> TeamMemberRole.OBSERVER
        }

        fun TeamMemberRole.toDatabaseModel() = when (this) {
            TeamMemberRole.PROJECT_MANAGER -> TeamMemberRoleDatabaseModel.PROJECT_MANAGER
            TeamMemberRole.TRANSLATOR -> TeamMemberRoleDatabaseModel.TRANSLATOR
            TeamMemberRole.EDITOR -> TeamMemberRoleDatabaseModel.EDITOR
            TeamMemberRole.PROOFREADER -> TeamMemberRoleDatabaseModel.PROOFREADER
            TeamMemberRole.SUBJECT_MATTER_EXPERT -> TeamMemberRoleDatabaseModel.SUBJECT_MATTER_EXPERT
            TeamMemberRole.PUBLISHER -> TeamMemberRoleDatabaseModel.PUBLISHER
            TeamMemberRole.OBSERVER -> TeamMemberRoleDatabaseModel.OBSERVER
        }
    }
}
package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.adapters.TeamMemberRoleRepositoryImpl.Mappers.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.adapters.TeamMemberRoleRepositoryImpl.Mappers.toDomain
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.entities.TeamMemberRoleDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.repositories.TeamMemberRoleJpaRepository
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.TeamMember
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.singlePage
import org.springframework.stereotype.Repository

@Repository
class TeamMemberRepositoryImpl(
    private val teamMemberRoleJpaRepository: TeamMemberRoleJpaRepository,
    private val userRepository: UserRepository
) : TeamMemberRepository {

    override fun getAll() = teamMemberRoleJpaRepository.findAll().toTeamMembers(userRepository)
    override fun get(id: TeamMemberId)  = teamMemberRoleJpaRepository.findAllByUserId(id.value).toTeamMember(userRepository)
    override fun get(ids: List<TeamMemberId>) = teamMemberRoleJpaRepository.findAllById(ids.map { it.value }).toTeamMembers(userRepository)
    override fun get(query: Query<TeamMember>): Page<TeamMember> = singlePage(getAll())
    override fun create(entity: TeamMember): TeamMember {
        teamMemberRoleJpaRepository.saveAll(entity.toDatabaseModel())
        return entity
    }

    override fun createAll(entities: List<TeamMember>): List<TeamMember> {
        teamMemberRoleJpaRepository.saveAll(entities.flatMap { it.toDatabaseModel() })
        return entities
    }

    override fun update(entity: TeamMember): TeamMember {
        teamMemberRoleJpaRepository.saveAll(entity.toDatabaseModel())
        return entity
    }

    override fun updateAll(entities: List<TeamMember>): List<TeamMember> {
        teamMemberRoleJpaRepository.saveAll(entities.flatMap { it.toDatabaseModel() })
        return entities
    }

    override fun delete(id: TeamMemberId) {
        teamMemberRoleJpaRepository.deleteAllByUserId(id.value)
    }

    override fun deleteAll(ids: List<TeamMemberId>) {
        teamMemberRoleJpaRepository.deleteAllById(ids.map { it.value })
    }

    override fun getAllByProjectId(projectId: ProjectId): List<TeamMember> {
        return teamMemberRoleJpaRepository.findAllByProjectId(projectId.value).toTeamMembers(userRepository)
    }

    override fun deleteAllByProjectId(projectId: ProjectId) {
        teamMemberRoleJpaRepository.deleteAllByProjectId(projectId.value)
    }

    override fun getByUserIdAndProjectId(userId: UserId, projectId: ProjectId): TeamMember? {
        return teamMemberRoleJpaRepository.findByUserIdAndProjectId(userId.value, projectId.value).toTeamMember(userRepository)
    }

    companion object Mappers {

        fun List<TeamMemberRoleDatabaseModel>.toTeamMembers(userRepository: UserRepository) = groupBy { it.userId }
            .map { (userId, teamMemberRoleDatabaseModels) ->
                TeamMember(
                    user = userRepository.get(UserId(userId)) ?: throw IllegalStateException("User not found"),
                    roles = teamMemberRoleDatabaseModels.map { it.toDomain() },
                    projectId = ProjectId(teamMemberRoleDatabaseModels.first().projectId)
                )
            }

        fun List<TeamMemberRoleDatabaseModel>.toTeamMember(userRepository: UserRepository): TeamMember? {
            if (this.isEmpty()) {
                return null
            }

            val userId = this.first().userId
            return TeamMember(
                user = userRepository.get(UserId(userId)) ?: throw IllegalStateException("User not found"),
                roles = this.map { it.toDomain() },
                projectId = ProjectId(this.first().projectId)
            )
        }

        fun TeamMember.toDatabaseModel() = this.roles.map { it.toDatabaseModel() }
    }
}

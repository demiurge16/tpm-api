package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.teammember.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.toPageable
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.teammember.entities.TeamMemberRoleDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.teammember.entities.ProjectRoleDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.teammember.repositories.TeamMemberRoleJpaRepository
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.teammember.specifications.TeamMemberRoleSpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRoleRepository
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import org.springframework.stereotype.Repository

@Repository
class TeamMemberRoleRepositoryImpl(
    private val jpaRepository: TeamMemberRoleJpaRepository,
    private val specificationBuilder: TeamMemberRoleSpecificationBuilder
) : TeamMemberRoleRepository {

    override fun getAll() = jpaRepository.findAll().map { it.toDomain() }
    override fun get(id: TeamMemberRoleId) = jpaRepository.findById(id.value).map { it.toDomain() }.orElse(null)
    override fun get(ids: List<TeamMemberRoleId>) = jpaRepository.findAllById(ids.map { it.value }).map { it.toDomain() }
    override fun get(query: Query<TeamMemberRole>): Page<TeamMemberRole> {
        val page = jpaRepository.findAll(specificationBuilder.build(query), query.toPageable())
        return Page(
            items = page.content.map { it.toDomain() },
            currentPage = page.number,
            totalPages = page.totalPages,
            totalItems = page.totalElements
        )
    }

    override fun create(entity: TeamMemberRole) = jpaRepository.save(entity.toDatabaseModel()).toDomain()
    override fun createAll(entities: List<TeamMemberRole>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain() }
    override fun update(entity: TeamMemberRole) = jpaRepository.save(entity.toDatabaseModel()).toDomain()
    override fun updateAll(entities: List<TeamMemberRole>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain() }
    override fun delete(id: TeamMemberRoleId) = jpaRepository.deleteById(id.value)
    override fun deleteAll(ids: List<TeamMemberRoleId>) = jpaRepository.deleteAllById(ids.map { it.value })

    companion object Mappers {
        fun TeamMemberRoleDatabaseModel.toDomain() = TeamMemberRole(
            id = TeamMemberRoleId(id),
            userId = UserId(userId),
            role = role.toDomain(),
            projectId = ProjectId(projectId)
        )

        fun TeamMemberRole.toDatabaseModel() = TeamMemberRoleDatabaseModel(
            id = id.value,
            userId = userId.value,
            role = role.toDatabaseModel(),
            projectId = projectId.value
        )

        fun ProjectRoleDatabaseModel.toDomain() = when (this) {
            ProjectRoleDatabaseModel.PROJECT_MANAGER -> ProjectRole.PROJECT_MANAGER
            ProjectRoleDatabaseModel.TRANSLATOR -> ProjectRole.TRANSLATOR
            ProjectRoleDatabaseModel.EDITOR -> ProjectRole.EDITOR
            ProjectRoleDatabaseModel.PROOFREADER -> ProjectRole.PROOFREADER
            ProjectRoleDatabaseModel.SUBJECT_MATTER_EXPERT -> ProjectRole.SUBJECT_MATTER_EXPERT
            ProjectRoleDatabaseModel.PUBLISHER -> ProjectRole.PUBLISHER
            ProjectRoleDatabaseModel.OBSERVER -> ProjectRole.OBSERVER
        }

        fun ProjectRole.toDatabaseModel() = when (this) {
            ProjectRole.PROJECT_MANAGER -> ProjectRoleDatabaseModel.PROJECT_MANAGER
            ProjectRole.TRANSLATOR -> ProjectRoleDatabaseModel.TRANSLATOR
            ProjectRole.EDITOR -> ProjectRoleDatabaseModel.EDITOR
            ProjectRole.PROOFREADER -> ProjectRoleDatabaseModel.PROOFREADER
            ProjectRole.SUBJECT_MATTER_EXPERT -> ProjectRoleDatabaseModel.SUBJECT_MATTER_EXPERT
            ProjectRole.PUBLISHER -> ProjectRoleDatabaseModel.PUBLISHER
            ProjectRole.OBSERVER -> ProjectRoleDatabaseModel.OBSERVER
        }
    }
}

package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.entities.TeamMemberRoleDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.*

interface TeamMemberRoleJpaRepository : JpaRepository<TeamMemberRoleDatabaseModel, UUID>, JpaSpecificationExecutor<TeamMemberRoleDatabaseModel> {
    fun findAllByProjectId(projectId: UUID): List<TeamMemberRoleDatabaseModel>
    fun findAllByUserId(userId: UUID): List<TeamMemberRoleDatabaseModel>
    fun deleteAllByProjectId(projectId: UUID)
    fun deleteAllByUserId(userId: UUID)
    fun findByUserIdAndProjectId(userId: UUID, projectId: UUID): List<TeamMemberRoleDatabaseModel>
}

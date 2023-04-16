package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.teammember.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.teammember.entities.TeamMemberDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TeamMemberJpaRepository : JpaRepository<TeamMemberDatabaseModel, UUID> {
    fun findAllByProjectId(projectId: UUID): List<TeamMemberDatabaseModel>
    fun deleteAllByProjectId(projectId: UUID)
    fun findByUserIdAndProjectId(userId: UUID, projectId: UUID): TeamMemberDatabaseModel?
}

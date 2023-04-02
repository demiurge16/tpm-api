package net.nuclearprometheus.tpm.applicationserver.adapters.database.teammember.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.database.teammember.entities.TeamMemberDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TeamMemberJpaRepository : JpaRepository<TeamMemberDatabaseModel, UUID>

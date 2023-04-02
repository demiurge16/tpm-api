package net.nuclearprometheus.tpm.applicationserver.adapters.database.project.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.database.project.entities.ProjectDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ProjectJpaRepository : JpaRepository<ProjectDatabaseModel, UUID>

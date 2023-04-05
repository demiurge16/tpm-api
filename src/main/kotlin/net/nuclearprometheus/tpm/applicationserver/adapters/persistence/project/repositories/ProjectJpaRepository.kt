package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.entities.ProjectDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ProjectJpaRepository : JpaRepository<ProjectDatabaseModel, UUID>

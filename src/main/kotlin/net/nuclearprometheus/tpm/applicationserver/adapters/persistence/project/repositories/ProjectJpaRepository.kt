package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.project.entities.ProjectDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.*

interface ProjectJpaRepository : JpaRepository<ProjectDatabaseModel, UUID>, JpaSpecificationExecutor<ProjectDatabaseModel>

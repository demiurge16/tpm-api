package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.ServiceTypeDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ServiceTypeJpaRepository : JpaRepository<ServiceTypeDatabaseModel, UUID>

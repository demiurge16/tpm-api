package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.IndustryDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface IndustryJpaRepository : JpaRepository<IndustryDatabaseModel, UUID>

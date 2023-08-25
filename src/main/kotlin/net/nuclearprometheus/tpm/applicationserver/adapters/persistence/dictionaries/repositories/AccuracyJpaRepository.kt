package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.dictionaries.entities.AccuracyDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.*

interface AccuracyJpaRepository : JpaRepository<AccuracyDatabaseModel, UUID>, JpaSpecificationExecutor<AccuracyDatabaseModel>

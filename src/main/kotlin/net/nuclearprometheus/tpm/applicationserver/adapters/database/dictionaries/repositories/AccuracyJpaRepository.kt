package net.nuclearprometheus.tpm.applicationserver.adapters.database.dictionaries.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.database.dictionaries.entities.AccuracyDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface AccuracyJpaRepository : JpaRepository<AccuracyDatabaseModel, UUID>

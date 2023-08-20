package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ThreadDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface ThreadJpaRepository : JpaRepository<ThreadDatabaseModel, UUID> {

    @Query("SELECT t FROM Thread t WHERE t.projectId = :projectId")
    fun findAllByProjectId(@Param("projectId") projectId: UUID): List<ThreadDatabaseModel>
}

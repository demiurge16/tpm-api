package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ReplyDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface ReplyJpaRepository : JpaRepository<ReplyDatabaseModel, UUID>, JpaSpecificationExecutor<ReplyDatabaseModel> {

    @Query("SELECT r FROM Reply r WHERE r.threadId = :threadId")
    fun findAllByThreadId(@Param("threadId") threadId: UUID): List<ReplyDatabaseModel>
}

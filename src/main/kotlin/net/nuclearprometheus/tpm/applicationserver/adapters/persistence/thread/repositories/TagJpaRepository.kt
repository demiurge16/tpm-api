package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.TagDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface TagJpaRepository : JpaRepository<TagDatabaseModel, UUID> {

    @Query("SELECT t FROM Tag t WHERE t.threadId = :threadId")
    fun findAllByThreadId(@Param("threadId") threadId: UUID): List<TagDatabaseModel>

    @Query("DELETE FROM Tag t WHERE t.threadId = :threadId")
    fun deleteAllByThreadId(@Param("threadId") threadId: UUID)
}
package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ThreadDislikeDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ThreadLikeDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface ThreadDislikeJpaRepository : JpaRepository<ThreadDislikeDatabaseModel, UUID> {

    @Query("SELECT r FROM ThreadDislike r WHERE r.threadId = :threadId")
    fun findAllByThreadId(@Param("threadId") threadId: UUID): List<ThreadDislikeDatabaseModel>

    @Query("SELECT r FROM ThreadDislike r WHERE r.threadId = :threadId AND r.authorId = :authorId")
    fun findByThreadIdAndAuthorId(@Param("threadId") threadId: UUID, @Param("authorId") authorId: UUID): Optional<ThreadDislikeDatabaseModel>

    @Query("DELETE FROM ThreadDislike r WHERE r.threadId = :threadId AND r.authorId = :authorId")
    fun deleteByThreadIdAndAuthorId(@Param("threadId") replyId: UUID, @Param("authorId") authorId: UUID)
}

package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ThreadLikeDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface ThreadLikeJpaRepository : JpaRepository<ThreadLikeDatabaseModel, UUID> {

    @Query("SELECT r FROM ThreadLike r WHERE r.threadId = :threadId")
    fun findAllByThreadId(@Param("threadId") threadId: UUID): List<ThreadLikeDatabaseModel>

    @Query("SELECT r FROM ThreadLike r WHERE r.threadId = :threadId AND r.authorId = :authorId")
    fun findByThreadIdAndAuthorId(@Param("threadId") threadId: UUID, @Param("authorId") authorId: UUID): Optional<ThreadLikeDatabaseModel>

    @Query("DELETE FROM ThreadLike r WHERE r.threadId = :threadId AND r.authorId = :authorId")
    fun deleteByThreadIdAndAuthorId(@Param("threadId") replyId: UUID, @Param("authorId") authorId: UUID)
}

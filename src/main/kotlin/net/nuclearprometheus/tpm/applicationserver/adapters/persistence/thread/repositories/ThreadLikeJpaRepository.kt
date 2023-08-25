package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ThreadLikeDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface ThreadLikeJpaRepository : JpaRepository<ThreadLikeDatabaseModel, UUID>, JpaSpecificationExecutor<ThreadLikeDatabaseModel> {

    @Query("SELECT r FROM ThreadLike r WHERE r.threadId = :threadId")
    fun findAllByThreadId(@Param("threadId") threadId: UUID): List<ThreadLikeDatabaseModel>

    @Query("SELECT r FROM ThreadLike r WHERE r.threadId = :threadId AND r.authorId = :authorId")
    fun findByThreadIdAndAuthorId(@Param("threadId") threadId: UUID, @Param("authorId") authorId: UUID): Optional<ThreadLikeDatabaseModel>

    @Modifying
    @Transactional
    @Query("DELETE FROM ThreadLike r WHERE r.threadId = :threadId AND r.authorId = :authorId")
    fun deleteByThreadIdAndAuthorId(@Param("threadId") replyId: UUID, @Param("authorId") authorId: UUID)
}

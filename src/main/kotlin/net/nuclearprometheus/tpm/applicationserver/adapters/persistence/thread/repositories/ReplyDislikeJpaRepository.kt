package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.repositories

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ReplyDislikeDatabaseModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface ReplyDislikeJpaRepository : JpaRepository<ReplyDislikeDatabaseModel, UUID> {

    @Query("SELECT r FROM ReplyDislike r WHERE r.replyId = :replyId")
    fun findAllByReplyId(@Param("replyId") replyId: UUID): List<ReplyDislikeDatabaseModel>

    @Query("SELECT r FROM ReplyDislike r WHERE r.replyId = :replyId AND r.authorId = :authorId")
    fun findByReplyIdAndAuthorId(@Param("replyId") replyId: UUID, @Param("authorId") authorId: UUID): Optional<ReplyDislikeDatabaseModel>

    @Modifying
    @Transactional
    @Query("DELETE FROM ReplyDislike r WHERE r.replyId = :replyId AND r.authorId = :authorId")
    fun deleteByReplyIdAndAuthorId(@Param("replyId") replyId: UUID, @Param("authorId") authorId: UUID)
}


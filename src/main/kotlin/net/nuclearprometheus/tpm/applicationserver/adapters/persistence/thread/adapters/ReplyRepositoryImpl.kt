package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ReplyDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.repositories.ReplyJpaRepository
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Reply
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ReplyId
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.*
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import org.springframework.stereotype.Repository

@Repository
class ReplyRepositoryImpl(
    private val jpaRepository: ReplyJpaRepository,
    private val userRepository: UserRepository,
    private val likeRepository: ReplyLikeRepository,
    private val dislikeRepository: ReplyDislikeRepository
) : ReplyRepository {

    override fun getAll() = jpaRepository.findAll()
        .map { it.toDomain(userRepository, likeRepository, dislikeRepository) }
    override fun get(id: ReplyId) = jpaRepository.findById(id.value)
        .map { it.toDomain(userRepository, likeRepository, dislikeRepository) }
        .orElse(null)
    override fun get(ids: List<ReplyId>) = jpaRepository.findAllById(ids.map { it.value })
        .map { it.toDomain(userRepository, likeRepository, dislikeRepository) }
    override fun create(entity: Reply) = jpaRepository.save(entity.toDatabaseModel())
        .toDomain(userRepository, likeRepository, dislikeRepository)
    override fun createAll(entities: List<Reply>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() })
        .map { it.toDomain(userRepository, likeRepository, dislikeRepository) }
    override fun update(entity: Reply) = jpaRepository.save(entity.toDatabaseModel())
        .toDomain(userRepository, likeRepository, dislikeRepository)
    override fun updateAll(entities: List<Reply>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() })
        .map { it.toDomain(userRepository, likeRepository, dislikeRepository) }
    override fun delete(id: ReplyId) = jpaRepository.deleteById(id.value)
    override fun deleteAll(ids: List<ReplyId>) = jpaRepository.deleteAllById(ids.map { it.value })
    override fun getAllByThreadId(threadId: ThreadId) = jpaRepository.findAllByThreadId(threadId.value)
        .map { it.toDomain(userRepository, likeRepository, dislikeRepository) }

    companion object Mappers {
        fun Reply.toDatabaseModel() = ReplyDatabaseModel(
            id = id.value,
            createdAt = createdAt,
            threadId = threadId.value,
            authorId = author.id.value,
            content = content,
            parentReplyId = parentReplyId?.let { it.value }
        )

        fun ReplyDatabaseModel.toDomain(
            userRepository: UserRepository,
            likeRepository: ReplyLikeRepository,
            dislikeRepository: ReplyDislikeRepository
        ) = Reply(
            id = ReplyId(id),
            author = userRepository.get(UserId(authorId)) ?: throw IllegalStateException("Reply author not found"),
            createdAt = createdAt,
            threadId = ThreadId(threadId),
            content = content,
            threadLikes = likeRepository.getAllByReplyId(ReplyId(id)),
            threadDislikes = dislikeRepository.getAllByReplyId(ReplyId(id)),
            parentReplyId = parentReplyId?.let { ReplyId(it) }
        )
    }
}
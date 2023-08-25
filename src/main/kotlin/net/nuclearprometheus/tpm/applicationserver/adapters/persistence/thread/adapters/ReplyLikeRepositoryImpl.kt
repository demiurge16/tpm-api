package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ReplyLikeDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.repositories.ReplyLikeJpaRepository
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ReplyLikeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import org.springframework.stereotype.Repository

@Repository
class ReplyLikeRepositoryImpl(
    private val jpaRepository: ReplyLikeJpaRepository,
    private val userRepository: UserRepository
) : ReplyLikeRepository {

    override fun getAll() = jpaRepository.findAll().map { it.toDomain(userRepository) }
    override fun get(id: ReplyLikeId) = jpaRepository.findById(id.value)
        .map { it.toDomain(userRepository) }
        .orElse(null)
    override fun get(ids: List<ReplyLikeId>) = jpaRepository.findAllById(ids.map { it.value })
        .map { it.toDomain(userRepository) }

    override fun get(query: Query<ReplyLike>): Page<ReplyLike> {
        TODO("Not yet implemented")
    }

    override fun create(entity: ReplyLike) = jpaRepository.save(entity.toDatabaseModel()).toDomain(userRepository)
    override fun createAll(entities: List<ReplyLike>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() })
        .map { it.toDomain(userRepository) }
    override fun update(entity: ReplyLike) = jpaRepository.save(entity.toDatabaseModel()).toDomain(userRepository)
    override fun updateAll(entities: List<ReplyLike>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() })
        .map { it.toDomain(userRepository) }
    override fun delete(id: ReplyLikeId) = jpaRepository.deleteById(id.value)
    override fun deleteAll(ids: List<ReplyLikeId>) = jpaRepository.deleteAllById(ids.map { it.value })
    override fun getAllByReplyId(replyId: ReplyId) =
        jpaRepository.findAllByReplyId(replyId.value).map { it.toDomain(userRepository) }
    override fun getByReplyIdAndUserId(replyId: ReplyId, userId: UserId) =
        jpaRepository.findByReplyIdAndAuthorId(replyId.value, userId.value)
            .map { it.toDomain(userRepository) }
            .orElse(null)

    override fun deleteByReplyIdAndUserId(replyId: ReplyId, userId: UserId) =
        jpaRepository.deleteByReplyIdAndAuthorId(replyId.value, userId.value)

    companion object Mappers {
        fun ReplyLike.toDatabaseModel() = ReplyLikeDatabaseModel(
            id = id.value,
            createdAt = createdAt,
            replyId = replyId.value,
            authorId = author.id.value
        )

        fun ReplyLikeDatabaseModel.toDomain(userRepository: UserRepository) = ReplyLike(
            id = ReplyLikeId(id),
            author = userRepository.get(UserId(authorId))
                ?: throw IllegalStateException("ReplyDislike author with id $authorId not found"),
            createdAt = createdAt,
            replyId = ReplyId(replyId)
        )
    }
}
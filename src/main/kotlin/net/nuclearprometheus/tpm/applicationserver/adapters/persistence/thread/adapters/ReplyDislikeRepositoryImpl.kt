package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ReplyDislikeDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.repositories.ReplyDislikeJpaRepository
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ReplyDislike
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ReplyDislikeId
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ReplyId
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ReplyLike
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ReplyDislikeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import org.springframework.stereotype.Repository

@Repository
class ReplyDislikeRepositoryImpl(
    private val jpaRepository: ReplyDislikeJpaRepository,
    private val userRepository: UserRepository
) : ReplyDislikeRepository {

    override fun getAll() = jpaRepository.findAll().map { it.toDomain(userRepository) }
    override fun get(id: ReplyDislikeId) = jpaRepository.findById(id.value)
        .map { it.toDomain(userRepository) }
        .orElse(null)
    override fun get(ids: List<ReplyDislikeId>) = jpaRepository.findAllById(ids.map { it.value })
        .map { it.toDomain(userRepository) }

    override fun get(query: Query<ReplyDislike>): Page<ReplyDislike> {
        TODO("Not yet implemented")
    }

    override fun create(entity: ReplyDislike) = jpaRepository.save(entity.toDatabaseModel()).toDomain(userRepository)
    override fun createAll(entities: List<ReplyDislike>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() })
        .map { it.toDomain(userRepository) }
    override fun update(entity: ReplyDislike) = jpaRepository.save(entity.toDatabaseModel()).toDomain(userRepository)
    override fun updateAll(entities: List<ReplyDislike>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() })
        .map { it.toDomain(userRepository) }
    override fun delete(id: ReplyDislikeId) = jpaRepository.deleteById(id.value)
    override fun deleteAll(ids: List<ReplyDislikeId>) = jpaRepository.deleteAllById(ids.map { it.value })
    override fun getAllByReplyId(replyId: ReplyId) =
        jpaRepository.findAllByReplyId(replyId.value).map { it.toDomain(userRepository) }
    override fun getByReplyIdAndUserId(replyId: ReplyId, userId: UserId) =
        jpaRepository.findByReplyIdAndAuthorId(replyId.value, userId.value)
            .map { it.toDomain(userRepository) }
            .orElse(null)

    override fun deleteByReplyIdAndUserId(replyId: ReplyId, userId: UserId) =
        jpaRepository.deleteByReplyIdAndAuthorId(replyId.value, userId.value)

    companion object Mappers {
        fun ReplyDislike.toDatabaseModel() = ReplyDislikeDatabaseModel(
            id = id.value,
            createdAt = createdAt,
            replyId = replyId.value,
            authorId = author.id.value
        )

        fun ReplyDislikeDatabaseModel.toDomain(userRepository: UserRepository) = ReplyDislike(
            id = ReplyDislikeId(id),
            author = userRepository.get(UserId(authorId))
                ?: throw IllegalStateException("ReplyDislike author with id $authorId not found"),
            createdAt = createdAt,
            replyId = ReplyId(replyId)
        )
    }
}

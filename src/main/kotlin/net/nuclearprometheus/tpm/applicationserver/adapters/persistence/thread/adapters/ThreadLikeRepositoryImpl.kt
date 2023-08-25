package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ThreadLikeDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.repositories.ThreadLikeJpaRepository
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadId
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadLike
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadLikeId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ThreadLikeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import org.springframework.stereotype.Repository

@Repository
class ThreadLikeRepositoryImpl(
    private val jpaRepository: ThreadLikeJpaRepository,
    private val userRepository: UserRepository
) : ThreadLikeRepository {

    override fun getAll() = jpaRepository.findAll().map { it.toDomain(userRepository) }
    override fun get(id: ThreadLikeId) = jpaRepository.findById(id.value)
        .map { it.toDomain(userRepository) }
        .orElse(null)
    override fun get(ids: List<ThreadLikeId>) = jpaRepository.findAllById(ids.map { it.value })
        .map { it.toDomain(userRepository) }

    override fun get(query: Query<ThreadLike>): Page<ThreadLike> {
        TODO("Not yet implemented")
    }

    override fun create(entity: ThreadLike) = jpaRepository.save(entity.toDatabaseModel()).toDomain(userRepository)
    override fun createAll(entities: List<ThreadLike>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() })
        .map { it.toDomain(userRepository) }
    override fun update(entity: ThreadLike) = jpaRepository.save(entity.toDatabaseModel()).toDomain(userRepository)
    override fun updateAll(entities: List<ThreadLike>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() })
        .map { it.toDomain(userRepository) }
    override fun delete(id: ThreadLikeId) = jpaRepository.deleteById(id.value)
    override fun deleteAll(ids: List<ThreadLikeId>) = jpaRepository.deleteAllById(ids.map { it.value })
    override fun getAllByThreadId(threadId: ThreadId) =
        jpaRepository.findAllByThreadId(threadId.value).map { it.toDomain(userRepository) }
    override fun getByThreadIdAndAuthorId(threadId: ThreadId, authorId: UserId) =
        jpaRepository.findByThreadIdAndAuthorId(threadId.value, authorId.value)
            .map { it.toDomain(userRepository) }
            .orElse(null)

    override fun deleteByThreadIdAndAuthorId(threadId: ThreadId, authorId: UserId) =
        jpaRepository.deleteByThreadIdAndAuthorId(threadId.value, authorId.value)

    companion object Mappers {
        fun ThreadLike.toDatabaseModel() = ThreadLikeDatabaseModel(
            id = id.value,
            createdAt = createdAt,
            threadId = threadId.value,
            authorId = author.id.value
        )

        fun ThreadLikeDatabaseModel.toDomain(userRepository: UserRepository) = ThreadLike(
            id = ThreadLikeId(id),
            author = userRepository.get(UserId(authorId))
                ?: throw IllegalStateException("ThreadLike author with id $authorId not found"),
            createdAt = createdAt,
            threadId = ThreadId(threadId)
        )
    }
}
package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.toPageable
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.expense.adapters.ExpenseRepositoryImpl.Mappers.toDomain
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ThreadDislikeDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.repositories.ThreadDislikeJpaRepository
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.specifications.ThreadDislikeSpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadDislike
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadDislikeId
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.ThreadDislikeRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import org.springframework.stereotype.Repository

@Repository
class ThreadDislikeRepositoryImpl(
    private val jpaRepository: ThreadDislikeJpaRepository,
    private val specificationBuilder: ThreadDislikeSpecificationBuilder,
    private val userRepository: UserRepository
) : ThreadDislikeRepository {

    override fun getAll() = jpaRepository.findAll().map { it.toDomain(userRepository) }
    override fun get(id: ThreadDislikeId) = jpaRepository.findById(id.value)
        .map { it.toDomain(userRepository) }
        .orElse(null)
    override fun get(ids: List<ThreadDislikeId>) = jpaRepository.findAllById(ids.map { it.value })
        .map { it.toDomain(userRepository) }

    override fun get(query: Query<ThreadDislike>): Page<ThreadDislike> {
        val page = jpaRepository.findAll(specificationBuilder.build(query), query.toPageable())
        return Page(
            items = page.content.map { it.toDomain(userRepository) },
            currentPage = page.number,
            totalPages = page.totalPages,
            totalItems = page.totalElements
        )
    }

    override fun create(entity: ThreadDislike) = jpaRepository.save(entity.toDatabaseModel()).toDomain(userRepository)
    override fun createAll(entities: List<ThreadDislike>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() })
        .map { it.toDomain(userRepository) }
    override fun update(entity: ThreadDislike) = jpaRepository.save(entity.toDatabaseModel()).toDomain(userRepository)
    override fun updateAll(entities: List<ThreadDislike>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() })
        .map { it.toDomain(userRepository) }
    override fun delete(id: ThreadDislikeId) = jpaRepository.deleteById(id.value)
    override fun deleteAll(ids: List<ThreadDislikeId>) = jpaRepository.deleteAllById(ids.map { it.value })
    override fun getAllByThreadId(threadId: ThreadId) = jpaRepository.findAllByThreadId(threadId.value)
        .map { it.toDomain(userRepository) }
    override fun getByThreadIdAndAuthorId(threadId: ThreadId, authorId: UserId) =
        jpaRepository.findByThreadIdAndAuthorId(threadId.value, authorId.value)
            .map { it.toDomain(userRepository) }
            .orElse(null)

    override fun deleteByThreadIdAndAuthorId(threadId: ThreadId, authorId: UserId) =
        jpaRepository.deleteByThreadIdAndAuthorId(threadId.value, authorId.value)

    companion object Mappers {
        fun ThreadDislike.toDatabaseModel() = ThreadDislikeDatabaseModel(
            id = id.value,
            createdAt = createdAt,
            threadId = threadId.value,
            authorId = author.id.value
        )

        fun ThreadDislikeDatabaseModel.toDomain(userRepository: UserRepository) = ThreadDislike(
            id = ThreadDislikeId(id),
            author = userRepository.get(UserId(authorId))
                ?: throw IllegalStateException("ThreadDislike author with id $authorId not found"),
            createdAt = createdAt,
            threadId = ThreadId(threadId)
        )
    }
}
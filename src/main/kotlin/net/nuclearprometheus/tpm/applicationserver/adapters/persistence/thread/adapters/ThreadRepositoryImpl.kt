package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.toPageable
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ThreadDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.ThreadStatusDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.repositories.ThreadJpaRepository
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.specifications.ThreadSpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Thread
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadId
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadStatus
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.*
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import org.springframework.stereotype.Repository

@Repository
class ThreadRepositoryImpl(
    private val jpaRepository: ThreadJpaRepository,
    private val specificationBuilder: ThreadSpecificationBuilder,
    private val userRepository: UserRepository,
    private val threadLikeRepository: ThreadLikeRepository,
    private val threadDislikeRepository: ThreadDislikeRepository,
    private val replyRepository: ReplyRepository,
    private val tagRepository: TagRepository
) : ThreadRepository {

    override fun getAll() = jpaRepository.findAll()
        .map { it.toDomain(userRepository, replyRepository, threadLikeRepository, threadDislikeRepository, tagRepository) }
    override fun get(id: ThreadId) = jpaRepository.findById(id.value)
        .map { it.toDomain(userRepository, replyRepository, threadLikeRepository, threadDislikeRepository, tagRepository) }
        .orElse(null)
    override fun get(ids: List<ThreadId>) = jpaRepository.findAllById(ids.map { it.value })
        .map { it.toDomain(userRepository, replyRepository, threadLikeRepository, threadDislikeRepository, tagRepository) }

    override fun get(query: Query<Thread>): Page<Thread> {
        val page = jpaRepository.findAll(specificationBuilder.build(query), query.toPageable())
        return Page(
            items = page.content
                .map {
                    it.toDomain(
                        userRepository,
                        replyRepository,
                        threadLikeRepository,
                        threadDislikeRepository,
                        tagRepository
                    )
                },
            currentPage = page.number,
            totalPages = page.totalPages,
            totalItems = page.totalElements
        )
    }

    override fun create(entity: Thread): Thread {
        val thread = jpaRepository.save(entity.toDatabaseModel())
        val likes = threadLikeRepository.createAll(entity.likes)
        val dislikes = threadDislikeRepository.createAll(entity.dislikes)
        val replies = replyRepository.createAll(entity.replies)
        val tags = tagRepository.createAll(entity.tags)

        return Thread(
            id = ThreadId(thread.id),
            title = thread.title,
            content = thread.content,
            author = userRepository.get(UserId(thread.authorId)) ?: throw NotFoundException("Author not found"),
            createdAt = thread.createdAt,
            status = thread.status.toDomain(),
            replies = replies,
            threadLikes = likes,
            threadDislikes = dislikes,
            tags = tags,
            projectId = ProjectId(thread.projectId)
        )
    }
    override fun createAll(entities: List<Thread>) = entities.map { create(it) }
    override fun update(entity: Thread): Thread {
        val thread = jpaRepository.save(entity.toDatabaseModel())
        tagRepository.deleteAllByThreadId(entity.id)
        val tags = tagRepository.createAll(entity.tags)

        return Thread(
            id = ThreadId(thread.id),
            title = thread.title,
            content = thread.content,
            author = userRepository.get(UserId(thread.authorId)) ?: throw NotFoundException("Author not found"),
            createdAt = thread.createdAt,
            status = thread.status.toDomain(),
            replies = replyRepository.getAllByThreadId(entity.id),
            threadLikes = threadLikeRepository.getAllByThreadId(entity.id),
            threadDislikes = threadDislikeRepository.getAllByThreadId(entity.id),
            tags = tags,
            projectId = ProjectId(thread.projectId)
        )
    }
    override fun updateAll(entities: List<Thread>) = entities.map { update(it) }
    override fun delete(id: ThreadId) = jpaRepository.deleteById(id.value)
    override fun deleteAll(ids: List<ThreadId>) = jpaRepository.deleteAllById(ids.map { it.value })
    override fun getAllByProjectId(projectId: ProjectId) = jpaRepository.findAllByProjectId(projectId.value)
        .map { it.toDomain(userRepository, replyRepository, threadLikeRepository, threadDislikeRepository, tagRepository) }

    companion object Mappers {
        fun Thread.toDatabaseModel() = ThreadDatabaseModel(
            id = this.id.value,
            title = this.title,
            content = this.content,
            createdAt = this.createdAt,
            status = this.status.toDatabaseModel(),
            authorId = this.author.id.value,
            projectId = this.projectId.value,
        )

        fun ThreadStatus.toDatabaseModel() = when(this) {
            ThreadStatus.DRAFT -> ThreadStatusDatabaseModel.DRAFT
            ThreadStatus.ACTIVE -> ThreadStatusDatabaseModel.ACTIVE
            ThreadStatus.FREEZE -> ThreadStatusDatabaseModel.FREEZE
            ThreadStatus.CLOSED -> ThreadStatusDatabaseModel.CLOSED
            ThreadStatus.ARCHIVED -> ThreadStatusDatabaseModel.ARCHIVED
            ThreadStatus.DELETED -> ThreadStatusDatabaseModel.DELETED
        }

        fun ThreadDatabaseModel.toDomain(
            userRepository: UserRepository,
            replyRepository: ReplyRepository,
            threadLikeRepository: ThreadLikeRepository,
            threadDislikeRepository: ThreadDislikeRepository,
            tagRepository: TagRepository,
        ) = Thread(
            id = ThreadId(this.id),
            title = this.title,
            content = this.content,
            createdAt = this.createdAt,
            status = this.status.toDomain(),
            author = userRepository.get(UserId(authorId)) ?: throw IllegalStateException("Author not found"),
            replies = replyRepository.getAllByThreadId(ThreadId(this.id)),
            threadLikes = threadLikeRepository.getAllByThreadId(ThreadId(this.id)),
            threadDislikes = threadDislikeRepository.getAllByThreadId(ThreadId(this.id)),
            tags = tagRepository.getAllByThreadId(ThreadId(this.id)),
            projectId = ProjectId(this.projectId),
        )

        fun ThreadStatusDatabaseModel.toDomain() = when(this) {
            ThreadStatusDatabaseModel.DRAFT -> ThreadStatus.DRAFT
            ThreadStatusDatabaseModel.ACTIVE -> ThreadStatus.ACTIVE
            ThreadStatusDatabaseModel.FREEZE -> ThreadStatus.FREEZE
            ThreadStatusDatabaseModel.CLOSED -> ThreadStatus.CLOSED
            ThreadStatusDatabaseModel.ARCHIVED -> ThreadStatus.ARCHIVED
            ThreadStatusDatabaseModel.DELETED -> ThreadStatus.DELETED
        }
    }
}
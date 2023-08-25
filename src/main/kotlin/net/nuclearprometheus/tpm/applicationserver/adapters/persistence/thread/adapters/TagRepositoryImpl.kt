package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.entities.TagDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.thread.repositories.TagJpaRepository
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Tag
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.TagId
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread.TagRepository
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import org.springframework.stereotype.Repository

@Repository
class TagRepositoryImpl(private val jpaRepository: TagJpaRepository) : TagRepository {

    override fun getAll() = jpaRepository.findAll().map { it.toDomain() }
    override fun get(id: TagId) = jpaRepository.findById(id.value).orElse(null)?.toDomain()
    override fun get(ids: List<TagId>) = jpaRepository.findAllById(ids.map { it.value }).map { it.toDomain() }
    override fun get(query: Query<Tag>): Page<Tag> {
        TODO("Not yet implemented")
    }
    override fun create(entity: Tag) = jpaRepository.save(entity.toDatabaseModel()).toDomain()
    override fun createAll(entities: List<Tag>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain() }
    override fun update(entity: Tag) = jpaRepository.save(entity.toDatabaseModel()).toDomain()
    override fun updateAll(entities: List<Tag>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain() }
    override fun delete(id: TagId) = jpaRepository.deleteById(id.value)
    override fun deleteAll(ids: List<TagId>) = jpaRepository.deleteAllById(ids.map { it.value })
    override fun getAllByThreadId(threadId: ThreadId) = jpaRepository.findAllByThreadId(threadId.value).map { it.toDomain() }
    override fun deleteAllByThreadId(threadId: ThreadId) = jpaRepository.deleteAllByThreadId(threadId.value)

    companion object Mappers {
        fun Tag.toDatabaseModel() = TagDatabaseModel(
            id = this.id.value,
            name = this.name,
            threadId = this.threadId.value
        )

        fun TagDatabaseModel.toDomain() = Tag(
            id = TagId(this.id),
            name = this.name,
            threadId = ThreadId(this.threadId)
        )
    }
}
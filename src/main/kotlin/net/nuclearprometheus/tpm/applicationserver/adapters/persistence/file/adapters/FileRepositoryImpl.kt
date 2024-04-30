package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.file.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.common.toPageable
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.file.entities.FileDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.file.repositories.FileJpaRepository
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.file.specifications.FileSpecificationFactory
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.File
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.FileId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.file.FileRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class FileRepositoryImpl(
    private val jpaRepository: FileJpaRepository,
    private val specificationBuilder: FileSpecificationFactory,
    private val userRepository: UserRepository
) : FileRepository {

    override fun getAll() = jpaRepository.findAll().map { it.toDomain(userRepository) }
    override fun get(id: FileId): File?  = jpaRepository.findById(id.value).map { it.toDomain(userRepository) }.orElse(null)
    override fun get(ids: List<FileId>) = jpaRepository.findAllById(ids.map { it.value }).map { it.toDomain(userRepository) }
    override fun get(query: Query<File>): Page<File> {
        val page = jpaRepository.findAll(specificationBuilder.create(query), query.toPageable())
        return Page(
            items = page.content.map { it.toDomain(userRepository) },
            currentPage = page.number,
            totalPages = page.totalPages,
            totalItems = page.totalElements
        )
    }
    override fun create(entity: File) = jpaRepository.save(entity.toDatabaseModel()).toDomain(userRepository)
    override fun createAll(entities: List<File>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain(userRepository) }
    override fun update(entity: File) = jpaRepository.save(entity.toDatabaseModel()).toDomain(userRepository)
    override fun updateAll(entities: List<File>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel() }).map { it.toDomain(userRepository) }
    override fun delete(id: FileId) = jpaRepository.deleteById(id.value)
    override fun deleteAll(ids: List<FileId>) = jpaRepository.deleteAllById(ids.map { it.value })
    override fun getAllByProjectId(projectId: ProjectId) = jpaRepository.findAllByProjectId(projectId.value).map { it.toDomain(userRepository) }
    override fun getAllByProjectIdAndQuery(projectId: ProjectId, query: Query<File>): Page<File> {
        val specification = specificationBuilder.create(query)
            .and { root, _, criteriaBuilder -> criteriaBuilder.equal(root.get<UUID>("projectId"), projectId.value) }
        val page = jpaRepository.findAll(specification, query.toPageable())
        return Page(
            items = page.content.map { it.toDomain(userRepository) },
            currentPage = page.number,
            totalPages = page.totalPages,
            totalItems = page.totalElements
        )
    }

    override fun deleteAllByProjectId(projectId: ProjectId) = jpaRepository.deleteAllByProjectId(projectId.value)

    companion object Mappers {

        fun FileDatabaseModel.toDomain(userRepository: UserRepository) = File(
            id = FileId(id),
            name = name,
            size = size,
            type = type,
            uploadTime = uploadTime,
            uploader = UserId(uploaderId).let {
                userRepository.get(it) ?: throw IllegalArgumentException("Uploader with id $it does not exist")
            },
            projectId = ProjectId(projectId),
            location = location
        )

        fun File.toDatabaseModel() = FileDatabaseModel(
            id = id.value,
            name = name,
            size = size,
            type = type,
            uploadTime = uploadTime,
            uploaderId = uploader.id.value,
            projectId = projectId.value,
            location = location
        )
    }
}

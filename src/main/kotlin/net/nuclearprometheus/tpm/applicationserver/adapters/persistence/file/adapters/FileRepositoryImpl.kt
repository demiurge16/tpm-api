package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.file.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.file.entities.FileDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.file.repositories.FileJpaRepository
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.teammember.adapters.TeamMemberRepositoryImpl.Mappers.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.File
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.FileId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.file.FileRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import org.springframework.stereotype.Repository

@Repository
class FileRepositoryImpl(
    private val jpaRepository: FileJpaRepository,
    private val teamMemberRepository: TeamMemberRepository
) : FileRepository {

    override fun getAll() = jpaRepository.findAll().map { it.toDomain() }
    override fun get(id: FileId): File?  = jpaRepository.findById(id.value).map { it.toDomain() }.orElse(null)
    override fun get(ids: List<FileId>) = jpaRepository.findAllById(ids.map { it.value }).map { it.toDomain() }
    override fun create(entity: File) = jpaRepository.save(entity.toDatabaseModel(teamMemberRepository)).toDomain()
    override fun createAll(entities: List<File>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel(teamMemberRepository) }).map { it.toDomain() }
    override fun update(entity: File) = jpaRepository.save(entity.toDatabaseModel(teamMemberRepository)).toDomain()
    override fun updateAll(entities: List<File>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel(teamMemberRepository) }).map { it.toDomain() }
    override fun delete(id: FileId) = jpaRepository.deleteById(id.value)
    override fun deleteAll(ids: List<FileId>) = jpaRepository.deleteAllById(ids.map { it.value })
    override fun getAllByProjectId(projectId: ProjectId) = jpaRepository.findAllByProjectId(projectId.value).map { it.toDomain() }
    override fun deleteAllByProjectId(projectId: ProjectId) = jpaRepository.deleteAllByProjectId(projectId.value)

    companion object Mappers {

        fun FileDatabaseModel.toDomain() = File(
            id = FileId(id),
            name = name,
            size = size,
            type = type,
            uploadTime = uploadTime,
            uploaderId = TeamMemberId(uploader.id),
            projectId = ProjectId(projectId),
            location = location
        )

        fun File.toDatabaseModel(teamMemberRepository: TeamMemberRepository) = FileDatabaseModel(
            id = id.value,
            name = name,
            size = size,
            type = type,
            uploadTime = uploadTime,
            uploader = teamMemberRepository.get(uploaderId)?.toDatabaseModel()
                ?: throw IllegalArgumentException("Uploader with id $uploaderId does not exist"),
            projectId = projectId.value,
            location = location
        )
    }
}

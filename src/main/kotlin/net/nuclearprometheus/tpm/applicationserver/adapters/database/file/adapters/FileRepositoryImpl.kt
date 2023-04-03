package net.nuclearprometheus.tpm.applicationserver.adapters.database.file.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.database.file.entities.FileDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.file.repositories.FileJpaRepository
import net.nuclearprometheus.tpm.applicationserver.adapters.database.teammember.adapters.TeamMemberRepositoryImpl.Mappers.toDatabaseModel
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

    override fun getAll() = jpaRepository.findAll().map { it.toDomainModel() }
    override fun get(id: FileId): File?  = jpaRepository.findById(id.value).map { it.toDomainModel() }.orElse(null)
    override fun get(ids: List<FileId>) = jpaRepository.findAllById(ids.map { it.value }).map { it.toDomainModel() }
    override fun create(entity: File) = jpaRepository.save(entity.toDatabaseModel(teamMemberRepository)).toDomainModel()
    override fun update(entity: File) = jpaRepository.save(entity.toDatabaseModel(teamMemberRepository)).toDomainModel()
    override fun delete(id: FileId) = jpaRepository.deleteById(id.value)

    companion object Mappers {

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

        fun FileDatabaseModel.toDomainModel() = File(
            id = FileId(id),
            name = name,
            size = size,
            type = type,
            uploadTime = uploadTime,
            uploaderId = TeamMemberId(uploader.id),
            projectId = ProjectId(projectId),
            location = location
        )
    }
}

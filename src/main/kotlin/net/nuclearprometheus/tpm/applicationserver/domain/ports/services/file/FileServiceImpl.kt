package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.file

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.File
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.FileId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.file.FileRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.logging.Logger

class FileServiceImpl(
    private val fileRepository: FileRepository,
    private val teamMemberRepository: TeamMemberRepository,
    private val projectRepository: ProjectRepository,
    private val logger: Logger
) : FileService {

    override fun create(
        name: String,
        size: Long,
        type: String,
        uploaderId: TeamMemberId,
        projectId: ProjectId,
        location: String
    ): File {
        projectRepository.get(projectId) ?: throw NotFoundException("Project with id $projectId does not exist")

        val teamMember = teamMemberRepository.get(uploaderId)
            ?: throw NotFoundException("Team member with id $uploaderId does not exist")

        val file = File(
            name = name,
            size = size,
            type = type,
            uploader = teamMember,
            projectId = projectId,
            location = location
        )

        return fileRepository.create(file)
    }

    override fun delete(id: FileId) {
        fileRepository.delete(id)
    }
}
package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.file

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.File
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.FileId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.file.FileRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.logging.Logger

class FileServiceImpl(
    private val fileRepository: FileRepository,
    private val teamMemberRepository: TeamMemberRepository,
    private val userRepository: UserRepository,
    private val projectRepository: ProjectRepository,
    private val logger: Logger
) : FileService {

    override fun create(
        name: String,
        size: Long,
        type: String,
        uploaderId: UserId,
        projectId: ProjectId,
        location: String
    ): File {
        projectRepository.get(projectId) ?: throw NotFoundException("Project with id $projectId does not exist")

        val uploader = userRepository.get(uploaderId)
            ?: throw NotFoundException("User with id $uploaderId does not exist")
        teamMemberRepository.getByUserIdAndProjectId(uploaderId, projectId)
            ?: throw NotFoundException("User with id $uploaderId is not a member of project with id $projectId")

        val file = File(
            name = name,
            size = size,
            type = type,
            uploader = uploader,
            projectId = projectId,
            location = location
        )

        return fileRepository.create(file)
    }

    override fun delete(id: FileId) {
        fileRepository.delete(id)
    }
}
package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.file

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.file.FileAccessException
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.project.ProjectAccessException
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.File
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.FileId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectRole
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserRole
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.file.FileRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.logging.Logger
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider

class FileServiceImpl(
    private val fileRepository: FileRepository,
    private val projectRepository: ProjectRepository,
    private val userContextProvider: UserContextProvider,
    private val logger: Logger
) : FileService {

    override fun create(
        name: String,
        size: Long,
        type: String,
        projectId: ProjectId,
        location: String
    ): File {
        val currentUser = userContextProvider.getCurrentUser()
        val project = projectRepository.get(projectId) ?: throw NotFoundException("Project with id $projectId does not exist")

        if (!currentUser.hasRole(UserRole.ADMIN) && !project.hasTeamMember(currentUser.id)) {
            logger.error("User ${currentUser.id} is not a member of project $projectId")
            throw ProjectAccessException("User ${currentUser.id} is not a member of project $projectId")
        }

        val file = File(
            name = name,
            size = size,
            type = type,
            uploader = currentUser,
            projectId = projectId,
            location = location
        )

        return fileRepository.create(file)
    }

    override fun delete(id: FileId) {
        val currentUser = userContextProvider.getCurrentUser()
        val file = fileRepository.get(id) ?: throw NotFoundException("File with id $id does not exist")
        val project = projectRepository.get(file.projectId) ?: throw IllegalStateException("Project with id ${file.projectId} does not exist")

        if (!currentUser.hasRole(UserRole.ADMIN) && !project.hasTeamMember(currentUser.id)) {
            logger.error("User ${currentUser.id} is not a member of project ${file.projectId}")
            throw ProjectAccessException("User ${currentUser.id} is not a member of project ${file.projectId}")
        }

        if (file.uploader.id != currentUser.id && !currentUser.hasRole(UserRole.ADMIN) && !project.hasTeamMemberWithRole(currentUser.id, ProjectRole.PROJECT_MANAGER)) {
            logger.error("User ${currentUser.id} is not the uploader of file $id and is not a project manager")
            throw FileAccessException("User ${currentUser.id} is not the uploader of file $id and is not a project manager")
        }

        fileRepository.delete(id)
    }
}
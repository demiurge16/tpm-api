package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project

import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.singlePage
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers.ProjectFileMapper.toView
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.file.FileRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.file.FileService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.file.FileStorageService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class ProjectFileApplicationService(
    private val fileRepository: FileRepository,
    private val fileService: FileService,
    private val fileStorageService: FileStorageService,
    private val teamMemberRepository: TeamMemberRepository,
    private val userContextProvider: UserContextProvider,
    @Value("\${app.file-storage.minio.bucket-name}") private val fileStorageLocation: String
) {

    private val logger = loggerFor(ProjectFileApplicationService::class.java)

    fun getFilesForProject(projectId: UUID) = with(logger) {
        info("getFiles($projectId)")

        singlePage(fileRepository.getAllByProjectId(ProjectId(projectId))).map { it.toView() }
    }

    fun addFile(projectId: UUID, request: MultipartFile) = with(logger) {
        info("addFile($projectId, $request)")

        val currentUser = userContextProvider.getCurrentUser()
        val uploader = teamMemberRepository.getByUserIdAndProjectId(currentUser.id, ProjectId(projectId))
            ?: throw IllegalStateException("User is not a team member")

        fileStorageService.store(fileStorageLocation, request.originalFilename!!, request.inputStream)

        fileService.create(
            name = request.originalFilename!!,
            size = request.size,
            type = request.contentType!!,
            uploaderId = uploader.id,
            projectId = ProjectId(projectId),
            location = fileStorageLocation
        ).toView()
    }
}
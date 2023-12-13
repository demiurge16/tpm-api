package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.file.responses.File as FileResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.file.mappers.FileMapper.toView
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.File
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.file.FileRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.file.FileService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.file.FileStorageService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
@Transactional(propagation = Propagation.REQUIRED)
class ProjectFileApplicationService(
    private val fileRepository: FileRepository,
    private val fileService: FileService,
    private val fileStorageService: FileStorageService,
    private val specificationBuilder: SpecificationBuilder<File>,
    private val teamMemberRepository: TeamMemberRepository,
    private val userContextProvider: UserContextProvider,
    @Value("\${app.file-storage.minio.bucket-name}") private val fileStorageLocation: String
) {

    private val logger = loggerFor(ProjectFileApplicationService::class.java)

    fun getFilesForProject(projectId: UUID, query: FilteredRequest<File>): Page<FileResponse> {
        logger.info("getFiles($projectId)")
        return fileRepository.getAllByProjectIdAndQuery(ProjectId(projectId), query.toQuery(specificationBuilder)).map { it.toView() }
    }

    fun addFile(projectId: UUID, request: MultipartFile) = with(logger) {
        info("addFile($projectId, $request)")

        val currentUser = userContextProvider.getCurrentUser()
        teamMemberRepository.getByUserIdAndProjectId(currentUser.id, ProjectId(projectId))
            ?: throw IllegalStateException("User with id ${currentUser.id} is not a member of project with id $projectId")

        fileStorageService.store(fileStorageLocation, request.originalFilename!!, request.inputStream)
        fileService.create(
            name = request.originalFilename!!,
            size = request.size,
            type = request.contentType!!,
            projectId = ProjectId(projectId),
            location = fileStorageLocation
        ).toView()
    }
}
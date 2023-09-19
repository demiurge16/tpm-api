package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.file

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.file.mappers.FileMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.file.responses.FileResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.File
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.FileId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.file.FileRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.file.FileService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.file.FileStorageService
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(propagation = Propagation.REQUIRED)
class FileApplicationService(
    private val repository: FileRepository,
    private val service: FileService,
    private val storageService: FileStorageService
) {

    private val logger = loggerFor(FileApplicationService::class.java)

    fun getFiles(query: FilteredRequest<File>): Page<FileResponse.File> {
        logger.info("getFiles($query)")
        return repository.get(query.toQuery()).map { it.toView() }
    }

    fun getFile(fileId: UUID): FileResponse.File {
        logger.info("getFile($fileId)")
        return repository.get(FileId(fileId))?.toView() ?: throw NotFoundException("File with id $fileId not found")
    }

    fun downloadFile(fileId: UUID): FileResponse.Download {
        logger.info("downloadFile($fileId)")
        val file = repository.get(FileId(fileId)) ?: throw NotFoundException("File with id $fileId not found")
        return FileResponse.Download(file.name, storageService.load(file.location, file.name))
    }

    fun deleteFile(fileId: UUID) {
        logger.info("deleteFile($fileId)")

        val file = repository.get(FileId(fileId)) ?: throw NotFoundException("File with id $fileId not found")
        storageService.delete(file.location, file.name)
        service.delete(file.id)
    }
}
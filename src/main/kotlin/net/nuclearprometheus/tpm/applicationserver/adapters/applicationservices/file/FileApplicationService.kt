package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.file

import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.singlePage
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.file.mappers.FileMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.file.responses.FileResponse
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.FileId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.file.FileRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.file.FileService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.file.FileStorageService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import java.util.*

@Service
class FileApplicationService(
    private val repository: FileRepository,
    private val service: FileService,
    private val storageService: FileStorageService
) {

    private val logger = loggerFor(FileApplicationService::class.java)

    fun getFiles() = with(logger) {
        info("getFiles()")

        singlePage(repository.getAll()).map { it.toView() }
    }

    fun getFile(fileId: UUID) = with(logger) {
        info("getFile($fileId)")

        repository.get(FileId(fileId))?.toView() ?: throw NotFoundException("File with id $fileId not found")
    }

    fun downloadFile(fileId: UUID) = with(logger) {
        info("downloadFile($fileId)")

        val file = repository.get(FileId(fileId)) ?: throw NotFoundException("File with id $fileId not found")

        FileResponse.Download(
            file.name,
            storageService.load(file.location, file.name)
        )
    }

    fun deleteFile(fileId: UUID) = with(logger) {
        info("deleteFile($fileId)")

        val file = repository.get(FileId(fileId)) ?: throw NotFoundException("File with id $fileId not found")

        storageService.delete(file.location, file.name)
        service.delete(file.id)
    }
}
package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.file

import net.nuclearprometheus.tpm.applicationserver.domain.model.file.File
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.FileId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId

class FileServiceImpl : FileService {
    override fun getAll(): List<File> {
        TODO("Not yet implemented")
    }

    override fun get(id: FileId): File? {
        TODO("Not yet implemented")
    }

    override fun create(
        name: String,
        size: Long,
        type: String,
        uploaderId: UserId,
        projectId: ProjectId,
        location: String
    ): File {
        TODO("Not yet implemented")
    }

    override fun delete(id: FileId) {
        TODO("Not yet implemented")
    }
}
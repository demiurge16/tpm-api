package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.file

import net.nuclearprometheus.tpm.applicationserver.domain.model.file.File
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.FileId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId

interface FileService {

    fun getAll(): List<File>
    fun get(id: FileId): File?
    fun create(
        name: String,
        size: Long,
        type: String,
        uploaderId: UserId,
        projectId: ProjectId,
        location: String
    ): File
    fun delete(id: FileId)
}
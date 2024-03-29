package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.file

import net.nuclearprometheus.tpm.applicationserver.domain.model.file.File
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.FileId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId

interface FileService {

    fun create(name: String, size: Long, type: String, projectId: ProjectId, location: String): File
    fun delete(id: FileId)
}

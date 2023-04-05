package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.file

import net.nuclearprometheus.tpm.applicationserver.domain.model.file.File
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.FileId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository

interface FileRepository : BaseRepository<File, FileId> {

    fun getAllByProjectId(projectId: ProjectId): List<File>
    fun deleteAllByProjectId(projectId: ProjectId)
}

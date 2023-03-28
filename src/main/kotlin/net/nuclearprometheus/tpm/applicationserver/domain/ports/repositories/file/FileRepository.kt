package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.file

import net.nuclearprometheus.tpm.applicationserver.domain.model.file.File
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.FileId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository

interface FileRepository : BaseRepository<File, FileId>

package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.file.responses.FileResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.file.responses.Uploader
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.File

object ProjectFileMapper {

    fun File.toView() = FileResponse.File(
        id = id.value,
        name = name,
        uploadTime = uploadTime,
        uploader = Uploader(
            userId = uploader.id.value,
            firstName = uploader.firstName,
            lastName = uploader.lastName,
            email = uploader.email
        ),
        projectId = projectId.value
    )
}
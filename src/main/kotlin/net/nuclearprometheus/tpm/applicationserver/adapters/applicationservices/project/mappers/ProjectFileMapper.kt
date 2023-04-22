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
            teamMemberId = uploader.id.value,
            userId = uploader.user.id.value,
            firstName = uploader.user.firstName,
            lastName = uploader.user.lastName,
            email = uploader.user.email
        ),
        projectId = projectId.value
    )
}
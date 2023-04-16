package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.file.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.file.responses.FileResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.File

object FileMapper {

    fun File.toView() = FileResponse.View(
        id = id.value,
        name = name,
        uploadTime = uploadTime,
        uploader = FileResponse.View.UploaderView(
            teamMemberId = uploader.id.value,
            userId = uploader.user.id.value,
            firstName = uploader.user.firstName,
            lastName = uploader.user.lastName,
            email = uploader.user.email
        ),
        projectId = projectId.value
    )
}
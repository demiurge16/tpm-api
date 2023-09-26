package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.file.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.file.responses.File as FileResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.mappers.UserMapper.toView
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.File

object FileMapper {

    fun File.toView() = FileResponse(
        id = id.value,
        name = name,
        uploadTime = uploadTime,
        uploader = uploader.toView(),
        projectId = projectId.value
    )
}
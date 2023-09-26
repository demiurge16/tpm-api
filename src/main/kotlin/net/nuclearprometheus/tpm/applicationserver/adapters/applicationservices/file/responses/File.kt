package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.file.responses

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.responses.User
import java.time.ZonedDateTime
import java.util.*

data class File(
    val id: UUID,
    val name: String,
    val uploadTime: ZonedDateTime,
    val uploader: User,
    val projectId: UUID
)

package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses

import java.time.ZonedDateTime
import java.util.*

data class ProjectDeadlineMoved(
    val id: UUID,
    val internalDeadline: ZonedDateTime,
    val externalDeadline: ZonedDateTime
)
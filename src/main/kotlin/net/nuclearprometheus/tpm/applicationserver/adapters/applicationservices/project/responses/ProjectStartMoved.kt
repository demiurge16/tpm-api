package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses

import java.time.ZonedDateTime
import java.util.*

data class ProjectStartMoved(
    val id: UUID,
    val expectedStart: ZonedDateTime
)
package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests

import java.time.ZonedDateTime

data class MoveProjectDeadline(val internalDeadline: ZonedDateTime, val externalDeadline: ZonedDateTime)

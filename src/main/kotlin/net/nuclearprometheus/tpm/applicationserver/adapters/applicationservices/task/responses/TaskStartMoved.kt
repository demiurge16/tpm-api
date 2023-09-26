package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses

import java.time.ZonedDateTime
import java.util.*

data class TaskStartMoved(val taskId: UUID, val start: ZonedDateTime)
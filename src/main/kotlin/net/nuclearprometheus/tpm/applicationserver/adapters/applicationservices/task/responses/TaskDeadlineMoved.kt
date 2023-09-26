package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses

import java.time.ZonedDateTime
import java.util.*

data class TaskDeadlineMoved(val taskId: UUID, val deadline: ZonedDateTime)
package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.requests

import java.time.ZonedDateTime

data class TaskMoveDeadline(val deadline: ZonedDateTime)
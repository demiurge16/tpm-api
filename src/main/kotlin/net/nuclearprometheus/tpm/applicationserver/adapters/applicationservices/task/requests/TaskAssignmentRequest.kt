package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.requests

import java.util.*

sealed class TaskAssignmentRequest {
    data class Assign(val teamMemberId: UUID) : TaskAssignmentRequest()
}
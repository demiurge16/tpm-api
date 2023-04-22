package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses

import java.util.*

data class Assignee(
    val teamMemberId: UUID,
    val userId: UUID,
    val firstName: String,
    val lastName: String,
    val email: String,
)

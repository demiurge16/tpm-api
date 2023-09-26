package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.responses.User
import java.util.*

data class TaskNewAssignee(val taskId: UUID, val newAssignee: User?)

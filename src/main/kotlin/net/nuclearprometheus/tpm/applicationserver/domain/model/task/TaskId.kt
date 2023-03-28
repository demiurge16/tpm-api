package net.nuclearprometheus.tpm.applicationserver.domain.model.task

import java.util.UUID

data class TaskId(val value: UUID = UUID.randomUUID())

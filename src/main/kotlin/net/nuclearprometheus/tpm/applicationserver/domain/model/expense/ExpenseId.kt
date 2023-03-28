package net.nuclearprometheus.tpm.applicationserver.domain.model.expense

import java.util.UUID

data class ExpenseId(val value: UUID = UUID.randomUUID())

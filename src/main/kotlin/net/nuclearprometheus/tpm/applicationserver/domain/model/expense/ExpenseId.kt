package net.nuclearprometheus.tpm.applicationserver.domain.model.expense

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Id
import java.util.*

class ExpenseId(value: UUID = UUID.randomUUID()): Id<UUID>(value)

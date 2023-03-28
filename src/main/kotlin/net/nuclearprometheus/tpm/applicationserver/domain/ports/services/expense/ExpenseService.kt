package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.expense

import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.Expense
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.ExpenseId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import java.time.ZonedDateTime

interface ExpenseService {

    fun getAll(): List<Expense>
    fun get(id: ExpenseId): Expense?

    fun create(
        projectId: ProjectId,
        userId: UserId,
        description: String,
        amount: Double,
        date: ZonedDateTime
    ): Expense

    fun delete(id: ExpenseId)
}
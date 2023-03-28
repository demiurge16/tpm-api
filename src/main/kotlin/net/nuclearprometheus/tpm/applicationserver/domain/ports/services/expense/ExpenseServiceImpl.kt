package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.expense

import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.Expense
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.ExpenseId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import java.time.ZonedDateTime

class ExpenseServiceImpl : ExpenseService {
    override fun getAll(): List<Expense> {
        TODO("Not yet implemented")
    }

    override fun get(id: ExpenseId): Expense? {
        TODO("Not yet implemented")
    }

    override fun create(
        projectId: ProjectId,
        userId: UserId,
        description: String,
        amount: Double,
        date: ZonedDateTime
    ): Expense {
        TODO("Not yet implemented")
    }

    override fun delete(id: ExpenseId) {
        TODO("Not yet implemented")
    }
}

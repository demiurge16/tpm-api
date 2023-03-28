package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.expense

import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.Expense
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.ExpenseId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import java.time.ZonedDateTime

interface ExpenseService {

    fun create(
        projectId: ProjectId,
        teamMemberId: TeamMemberId,
        description: String,
        amount: Double,
        date: ZonedDateTime
    ): Expense

    fun delete(id: ExpenseId)
}
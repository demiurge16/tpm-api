package net.nuclearprometheus.tpm.applicationserver.domain.model.expense

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMember
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import java.time.ZonedDateTime

class Expense(
    val id: ExpenseId = ExpenseId(),
    projectId: ProjectId,
    teamMemberId: TeamMemberId,
    description: String,
    amount: Double,
    date: ZonedDateTime
) {
    var projectId = projectId; private set
    var teamMemberId = teamMemberId; private set
    var description = description; private set
    var amount = amount; private set
    var date = date; private set
}

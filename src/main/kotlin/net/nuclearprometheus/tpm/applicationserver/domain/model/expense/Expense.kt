package net.nuclearprometheus.tpm.applicationserver.domain.model.expense

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Currency
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ExpenseCategory
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import java.math.BigDecimal
import java.time.ZonedDateTime

class Expense(
    id: ExpenseId = ExpenseId(),
    description: String,
    category: ExpenseCategory,
    amount: BigDecimal,
    currency: Currency,
    date: ZonedDateTime,
    teamMemberId: TeamMemberId,
    projectId: ProjectId
) : Entity<ExpenseId>(id) {

    var description = description; private set
    var category = category; private set
    var amount = amount; private set
    var currency = currency; private set
    var date = date; private set
    var teamMemberId = teamMemberId; private set
    var projectId = projectId; private set
}

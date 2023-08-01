package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.response.*
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.response.TeamMember as TeamMemberResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.Expense
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMember

object ExpenseMapper {

    fun Expense.toView(teamMember: TeamMember, project: Project) = ExpenseResponse.Expense(
        id = id.value,
        description = description,
        category = ExpenseCategory(
            id = category.id.value,
            name = category.name,
            description = category.description
        ),
        amount = amount,
        currency = Currency(
            code = currency.id.value,
            name = currency.name
        ),
        date = date,
        teamMember = TeamMemberResponse(
            id = teamMember.id.value,
            userId = teamMember.user.id.value,
            firstName = teamMember.user.firstName,
            lastName = teamMember.user.lastName,
            email = teamMember.user.email,
            role = Role(
                role = teamMember.role,
                title = teamMember.role.title,
                description = teamMember.role.description
            )
        ),
        project = ProjectShortView(
            id = project.id.value,
            title = project.title,
            status = ProjectStatus(
                status = project.status,
                title = project.status.title,
                description = project.status.shortDescription
            ),
        )
    )
}
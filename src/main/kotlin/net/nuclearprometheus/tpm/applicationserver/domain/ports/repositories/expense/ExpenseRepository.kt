package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.expense

import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.Expense
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.ExpenseId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page

interface ExpenseRepository : BaseRepository<Expense, ExpenseId> {

    fun getAllByProjectId(projectId: ProjectId): List<Expense>
    fun getAllByProjectIdAndQuery(projectId: ProjectId, query: Query<Expense>): Page<Expense>
    fun deleteAllByProjectId(projectId: ProjectId)
}

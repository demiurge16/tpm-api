package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.expense

import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.Expense
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.ExpenseId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository

interface ExpenseRepository : BaseRepository<Expense, ExpenseId>

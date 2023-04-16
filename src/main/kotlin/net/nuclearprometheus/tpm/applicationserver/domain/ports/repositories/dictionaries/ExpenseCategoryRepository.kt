package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ExpenseCategory
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ExpenseCategoryId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository

interface ExpenseCategoryRepository : BaseRepository<ExpenseCategory, ExpenseCategoryId>

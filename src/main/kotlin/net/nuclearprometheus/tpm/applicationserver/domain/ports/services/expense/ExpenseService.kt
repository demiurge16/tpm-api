package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.expense

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.CurrencyCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ExpenseCategoryId
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.Expense
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.ExpenseId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import java.math.BigDecimal
import java.time.ZonedDateTime

interface ExpenseService {

    fun create(
        description: String,
        category: ExpenseCategoryId,
        amount: BigDecimal,
        currencyCode: CurrencyCode,
        date: ZonedDateTime,
        spenderId: UserId,
        projectId: ProjectId
    ): Expense

    fun delete(id: ExpenseId)
}
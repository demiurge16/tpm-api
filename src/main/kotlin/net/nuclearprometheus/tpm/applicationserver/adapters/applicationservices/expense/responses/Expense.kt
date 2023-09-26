package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.responses

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.Currency
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.ExpenseCategory
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectShortView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.responses.User
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

data class Expense(
    val id: UUID,
    val description: String,
    val category: ExpenseCategory,
    val amount: BigDecimal,
    val currency: Currency,
    val date: ZonedDateTime,
    val teamMember: User,
    val project: ProjectShortView
)

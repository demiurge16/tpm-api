package net.nuclearprometheus.tpm.applicationserver.domain.model.expense

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.ValidationError
import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Currency
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.ExpenseCategory
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User
import net.nuclearprometheus.tpm.applicationserver.domain.validator.validate
import java.math.BigDecimal
import java.time.ZonedDateTime

class Expense(
    id: ExpenseId = ExpenseId(),
    description: String,
    category: ExpenseCategory,
    amount: BigDecimal,
    currency: Currency,
    date: ZonedDateTime,
    spender: User,
    projectId: ProjectId
) : Entity<ExpenseId>(id) {

    init {
        validate {
            assert { description.isNotBlank() } otherwise {
                ValidationError("description", "Description cannot be blank")
            }
            assert { amount > BigDecimal.ZERO } otherwise {
                ValidationError("amount", "Amount must be greater than zero")
            }
            assert { category.active } otherwise {
                ValidationError("category", "Category must be active")
            }
        }
    }

    var description = description; private set
    var category = category; private set
    var amount = amount; private set
    var currency = currency; private set
    var date = date; private set
    var spender = spender; private set
    var projectId = projectId; private set
}

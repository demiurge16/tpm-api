package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.expense.requests

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.expense.Expense

class ListExpenses(
    page: Int?,
    size: Int?,
    sort: String?,
    search: String?
) : FilteredRequest<Expense>( page, size, sort, search) {

    override fun toString(): String {
        return "ExpenseRequest.List(page=$page, size=$size, sort=$sort, search=$search)"
    }
}

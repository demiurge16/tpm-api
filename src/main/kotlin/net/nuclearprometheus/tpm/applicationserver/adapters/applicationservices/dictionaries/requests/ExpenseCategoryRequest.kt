package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests

sealed class ExpenseCategoryRequest {
    data class Create(val name: String, val description: String) : ExpenseCategoryRequest()
    data class Update(val name: String, val description: String) : ExpenseCategoryRequest()
}
package net.nuclearprometheus.tpm.applicationserver.adapters.common.responses

data class Page<TEntity>(val items: List<TEntity>, val totalPages: Int, val totalElements: Int) {

    fun <TResult> map(mapper: (TEntity) -> TResult): Page<TResult> =
        Page(items.map(mapper), totalPages, totalElements)
}
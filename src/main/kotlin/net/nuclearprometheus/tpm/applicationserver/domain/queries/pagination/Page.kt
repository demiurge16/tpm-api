package net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination

data class Page<T>(val items: List<T>, val currentPage: Int, val totalPages: Int, val totalItems: Long) {
    val hasNextPage = currentPage < totalPages
    val hasPreviousPage = currentPage > 1

    fun <R> map(mapper: (T) -> R): Page<R> {
        return Page(items.map(mapper), currentPage, totalPages, totalItems)
    }
}

package net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination

data class Page<T>(val items: List<T>, val currentPage: Int, val totalPages: Int, val totalItems: Long) {
    val hasNextPage = currentPage < totalPages
    val hasPreviousPage = currentPage > 1

    fun <R> map(mapper: (T) -> R): Page<R> {
        return Page(items.map(mapper), currentPage, totalPages, totalItems)
    }
}

fun <T> singlePage(items: List<T>): Page<T> = Page(items, 1, items.size, items.size.toLong())
fun <T> emptyPage(): Page<T> = Page(listOf(), 0, 0, 0)

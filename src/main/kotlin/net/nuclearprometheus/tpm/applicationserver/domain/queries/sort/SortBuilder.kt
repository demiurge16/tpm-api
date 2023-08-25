package net.nuclearprometheus.tpm.applicationserver.domain.queries.sort

fun unsorted() = emptyList<Sort>()

fun sort(init: SortBuilder.() -> Unit): List<Sort> {
    val builder = SortBuilder()
    builder.init()
    return builder.build()
}

class SortBuilder {
    private val sorts = mutableListOf<Sort>()

    fun ascending(field: String): SortBuilder {
        sorts.add(Sort(field, SortDirection.ASC))
        return this
    }

    fun descending(field: String): SortBuilder {
        sorts.add(Sort(field, SortDirection.DESC))
        return this
    }

    fun build(): List<Sort> {
        return sorts.toList()
    }
}
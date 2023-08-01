package net.nuclearprometheus.tpm.applicationserver.adapters.common.responses

// TODO: make this an interface
interface Pageable<TEntity> {
    val items: List<TEntity>
    val totalPages: Int
    val totalElements: Int

    fun <TResult> map(mapper: (TEntity) -> TResult): Pageable<TResult>
}

class PageableImpl<TEntity>(
    override val items: List<TEntity>,
    override val totalPages: Int,
    override val totalElements: Int,
) : Pageable<TEntity> {

    override fun <TResult> map(mapper: (TEntity) -> TResult): Pageable<TResult> = PageableImpl(
        items = items.map(mapper),
        totalPages = totalPages,
        totalElements = totalElements,
    )
}

fun <TEntity : Any> singlePage(items: List<TEntity>): Pageable<TEntity> = PageableImpl(items, 1, items.size)
fun <TEntity : Any> emptyPage(): Pageable<TEntity> = PageableImpl(listOf(), 0, 0)

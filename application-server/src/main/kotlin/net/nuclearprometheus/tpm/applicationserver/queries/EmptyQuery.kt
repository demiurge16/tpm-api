package net.nuclearprometheus.tpm.applicationserver.queries

fun <TEntity : Any> emptyQuery() = parseQuery<TEntity>("")
package net.nuclearprometheus.tpm.applicationserver.queries

fun <TEntity : Any> emptyQuery() = Query<TEntity>(emptyList())

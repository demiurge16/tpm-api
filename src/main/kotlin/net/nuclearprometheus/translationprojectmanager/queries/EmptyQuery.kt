package net.nuclearprometheus.translationprojectmanager.queries

fun <TEntity : Any> emptyQuery() = parseQuery<TEntity>("")
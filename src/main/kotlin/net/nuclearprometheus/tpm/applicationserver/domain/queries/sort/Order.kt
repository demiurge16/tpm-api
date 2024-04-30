package net.nuclearprometheus.tpm.applicationserver.domain.queries.sort

data class Order<TEntity : Any>(val name: String, val direction: Direction)

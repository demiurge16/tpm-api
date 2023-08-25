package net.nuclearprometheus.tpm.applicationserver.domain.queries.search

data class Filter(val field: String, val operation: OperationType, val value: Any)
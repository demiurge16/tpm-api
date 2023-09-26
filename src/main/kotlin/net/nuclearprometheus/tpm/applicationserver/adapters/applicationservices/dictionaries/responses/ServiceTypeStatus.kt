package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses

import java.util.*

data class ServiceTypeStatus(
    val id: UUID,
    val active: Boolean
)
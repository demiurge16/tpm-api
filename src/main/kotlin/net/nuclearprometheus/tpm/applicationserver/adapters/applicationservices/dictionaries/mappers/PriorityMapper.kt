package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.PriorityStatus
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.Priority as PriorityResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Priority

object PriorityMapper {

    fun Priority.toView() = PriorityResponse(
        id = id.value,
        name = name,
        description = description,
        emoji = emoji,
        value = value,
        active = active
    )

    fun Priority.toActivityStatus() = PriorityStatus(
        id = id.value,
        active = active
    )
}
package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.PriorityResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Priority

object PriorityMapper {

    fun Priority.toView() = PriorityResponse.View(
        id = id.value,
        name = name,
        description = description,
        emoji = emoji,
        value = value,
        active = active
    )

    fun Priority.toActivityStatus() = PriorityResponse.ActivityStatus(
        id = id.value,
        active = active
    )
}
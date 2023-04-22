package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.IndustryResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Industry

object IndustryMapper {

    fun Industry.toView() = IndustryResponse.Industry(
        id = id.value,
        name = name,
        description = description,
        active = active,
    )

    fun Industry.toActivityStatus() = IndustryResponse.Status(
        id = id.value,
        active = active,
    )
}
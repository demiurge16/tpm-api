package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.Industry as IndustryResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.IndustryStatus
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Industry

object IndustryMapper {

    fun Industry.toView() = IndustryResponse(
        id = id.value,
        name = name,
        description = description,
        active = active,
    )

    fun Industry.toActivityStatus() = IndustryStatus(
        id = id.value,
        active = active,
    )
}
package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.AccuracyStatus
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.Accuracy as AccuracyResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Accuracy

object AccuracyMapper {

    fun Accuracy.toView() = AccuracyResponse(
        id = id.value,
        name = name,
        description = description,
        active = active
    )

    fun Accuracy.toActivityStatus() = AccuracyStatus(id = id.value, active = active)
}

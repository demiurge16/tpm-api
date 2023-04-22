package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.AccuracyResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Accuracy

object AccuracyMapper {
    fun Accuracy.toView() = AccuracyResponse.Accuracy(
        id = id.value,
        name = name,
        description = description,
        active = active
    )

    fun Accuracy.toActivityStatus() = AccuracyResponse.Status(id = id.value, active = active)
}
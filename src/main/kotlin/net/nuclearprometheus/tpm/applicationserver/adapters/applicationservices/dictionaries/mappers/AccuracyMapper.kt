package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.AccuracyResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Accuracy

object AccuracyMapper {
    fun Accuracy.toView() = AccuracyResponse.View(
        id = id.value,
        name = name,
        description = description,
        active = active
    )

    fun Accuracy.toActivityStatus() = AccuracyResponse.ActivityStatus(id = id.value, active = active)
}
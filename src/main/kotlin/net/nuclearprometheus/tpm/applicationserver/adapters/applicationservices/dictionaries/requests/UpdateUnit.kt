package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Measurement

data class UpdateUnit(val name: String, val description: String, val volume: Int, val measurement: Measurement)
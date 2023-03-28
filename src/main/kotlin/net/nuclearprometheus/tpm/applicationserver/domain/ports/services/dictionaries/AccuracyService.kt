package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Accuracy
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.AccuracyId

interface AccuracyService {
    fun create(name: String, description: String): Accuracy
    fun update(id: AccuracyId, name: String, description: String): Accuracy
    fun activate(id: AccuracyId): Accuracy
    fun deactivate(id: AccuracyId): Accuracy
}

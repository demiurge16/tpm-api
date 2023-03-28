package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Accuracy
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.AccuracyId

interface AccuracyService {

    fun getAll(): List<Accuracy>
    fun get(id: AccuracyId): Accuracy?
    fun create(name: String, description: String): Accuracy
    fun update(id: AccuracyId, name: String, description: String): Accuracy
    fun activate(id: AccuracyId)
    fun deactivate(id: AccuracyId)
}

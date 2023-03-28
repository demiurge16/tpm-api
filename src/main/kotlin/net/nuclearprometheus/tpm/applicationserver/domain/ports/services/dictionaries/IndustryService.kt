package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Industry
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.IndustryId

interface IndustryService {

    fun getAll(): List<Industry>
    fun get(id: IndustryId): Industry?
    fun create(name: String, description: String): Industry
    fun update(id: IndustryId, name: String, description: String): Industry
    fun activate(id: IndustryId): Industry
    fun deactivate(id: IndustryId): Industrys
}
package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Industry
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.IndustryId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.IndustryRepository

class IndustryServiceImpl(
    private val industryRepository: IndustryRepository
) : IndustryService {
    override fun getAll(): List<Industry> {
        return industryRepository.getAll()
    }

    override fun get(id: IndustryId): Industry? {
        return industryRepository.get(id)
    }

    override fun create(name: String, description: String): Industry {
        val industry = Industry(name = name, description = description)

        return industryRepository.create(industry)
    }

    override fun update(id: IndustryId, name: String, description: String): Industry {
        val industry = industryRepository.get(id) ?: throw IllegalArgumentException("Industry with id $id does not exist")
        industry.update(name, description)

        return industryRepository.update(industry)
    }

    override fun activate(id: IndustryId): Industry {
        val industry = industryRepository.get(id) ?: throw IllegalArgumentException("Industry with id $id does not exist")
        industry.activate()

        return industryRepository.update(industry)
    }

    override fun deactivate(id: IndustryId): Industry {
        val industry = industryRepository.get(id) ?: throw IllegalArgumentException("Industry with id $id does not exist")
        industry.deactivate()

        return industryRepository.update(industry)
    }
}
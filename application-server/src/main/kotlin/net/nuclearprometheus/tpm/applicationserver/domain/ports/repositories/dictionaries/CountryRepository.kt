package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Country

interface CountryRepository {
    fun getAll(): List<Country>
    fun getByCode(code: String): Country?
    fun getByNameLike(name: String): List<Country>
}

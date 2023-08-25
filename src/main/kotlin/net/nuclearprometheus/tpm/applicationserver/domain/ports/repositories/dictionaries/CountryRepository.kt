package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Country
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page

interface CountryRepository {
    fun getAll(): List<Country>
    fun get(query: Query<Country>): Page<Country>
    fun getByCode(code: String): Country?
    fun getByNameLike(name: String): List<Country>
}

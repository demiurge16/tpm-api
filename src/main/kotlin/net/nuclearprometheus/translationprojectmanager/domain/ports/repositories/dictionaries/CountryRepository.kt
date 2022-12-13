package net.nuclearprometheus.translationprojectmanager.domain.ports.repositories.dictionaries

import net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries.Country

interface CountryRepository {
    fun getAll(): List<Country>
    fun getByCode(code: String): Country?
    fun getByNameLike(name: String): List<Country>
}

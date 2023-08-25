package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Language
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageCode
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page

interface LanguageRepository {
    fun getAll(): List<Language>
    fun get(code: LanguageCode): Language?
    fun get(codes: List<LanguageCode>): List<Language>
    fun get(query: Query<Language>): Page<Language>
    fun getByNameLike(name: String): List<Language>
}

package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Language
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageCode

interface LanguageRepository {
    fun getAll(): List<Language>
    fun getByCode(code: LanguageCode): Language?
    fun getByNameLike(name: String): List<Language>
}

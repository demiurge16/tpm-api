package net.nuclearprometheus.translationprojectmanager.domain.ports.repositories.dictionaries

import net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries.Language
import net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries.LanguageCode

interface LanguageRepository {
    fun getAll(): List<Language>
    fun getByCode(code: LanguageCode): Language?
    fun getByNameLike(name: String): List<Language>
}

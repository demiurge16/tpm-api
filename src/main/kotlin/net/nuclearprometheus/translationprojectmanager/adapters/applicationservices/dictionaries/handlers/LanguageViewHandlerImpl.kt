package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.handlers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.mappers.toView
import net.nuclearprometheus.translationprojectmanager.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.translationprojectmanager.domain.model.dictionaries.LanguageCode
import net.nuclearprometheus.translationprojectmanager.domain.ports.repositories.dictionaries.LanguageRepository
import org.springframework.stereotype.Service

@Service
class LanguageViewHandlerImpl(private val repository: LanguageRepository) : LanguageViewHandler {

    override fun getLanguages() = repository.getAll().map { it.toView() }
    override fun getLanguage(code: String) = repository.getByCode(LanguageCode(code))?.toView() ?: throw NotFoundException("Currency with code $code not found")
    override fun getLanguageByNameLike(name: String) = repository.getByNameLike(name).map { it.toView() }
}
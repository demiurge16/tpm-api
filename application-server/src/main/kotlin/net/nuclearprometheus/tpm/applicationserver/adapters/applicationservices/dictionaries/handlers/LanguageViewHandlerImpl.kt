package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.toView
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageCode
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.LanguageRepository
import org.springframework.stereotype.Service

@Service
class LanguageViewHandlerImpl(private val repository: LanguageRepository) : LanguageViewHandler {

    override fun getLanguages() = repository.getAll().map { it.toView() }
    override fun getLanguage(code: String) = repository.getByCode(LanguageCode(code))?.toView() ?: throw NotFoundException("Currency with code $code not found")
    override fun getLanguageByNameLike(name: String) = repository.getByNameLike(name).map { it.toView() }
}
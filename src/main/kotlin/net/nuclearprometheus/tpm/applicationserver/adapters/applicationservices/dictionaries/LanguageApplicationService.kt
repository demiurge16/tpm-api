package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.LanguageMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.applyQuery
import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.singlePage
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Language
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageCode
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageScope
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageType
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.LanguageRepository
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class LanguageApplicationService(private val repository: LanguageRepository) {

    private val logger = loggerFor(this::class.java)

    @Cacheable("languages-cache")
    fun getLanguages(query: FilteredRequest<Language>) =
        with(logger) {
            info("getLanguages($query)")

            repository.getAll().applyQuery(query).map { it.toView() }
        }

    @Cacheable("languages-cache")
    fun getLanguage(code: String) =
        with(logger) {
            info("getLanguage($code)")

            repository.get(LanguageCode(code))?.toView() ?: throw NotFoundException("Language with code $code not found")
        }

    @Cacheable("languages-cache")
    fun getLanguageByNameLike(name: String) =
        with(logger) {
            info("getLanguageByNameLike($name)")

            singlePage(repository.getByNameLike(name)).map { it.toView() }
        }

    fun getLanguageScopes() =
        with(logger) {
            info("getLanguageScopes")
            LanguageScope.values().map { it.toView() }
        }

    fun getLanguageTypes() =
        with(logger) {
            info("getLanguageTypes")
            LanguageType.values().map { it.toView() }
        }
}

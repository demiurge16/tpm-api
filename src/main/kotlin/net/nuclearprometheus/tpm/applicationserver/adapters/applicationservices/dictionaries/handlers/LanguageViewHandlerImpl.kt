package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.applyQuery
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Language
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageCode
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.LanguageRepository
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class LanguageViewHandlerImpl(private val repository: LanguageRepository) : LanguageViewHandler {

    private val logger = loggerFor(this::class.java)

    @Cacheable("languages-cache")
    override fun getLanguages(query: FilteredRequest<Language>) =
        with(logger) {
            info("Language view handler, method getLanguages")
            info("Query: $query")

            repository.getAll().applyQuery(query).map { it.toView() }
        }

    @Cacheable("languages-cache")
    override fun getLanguage(code: String) =
        with(logger) {
            info("Language view handler, method getLanguage")
            info("Code: $code")

            repository.get(LanguageCode(code))?.toView() ?: throw NotFoundException("Language with code $code not found")
        }

    @Cacheable("languages-cache")
    override fun getLanguageByNameLike(name: String) =
        with(logger) {
            info("Language view handler, method getLanguageByNameLike")
            info("Name: $name")

            repository.getByNameLike(name).map { it.toView() }
        }

}
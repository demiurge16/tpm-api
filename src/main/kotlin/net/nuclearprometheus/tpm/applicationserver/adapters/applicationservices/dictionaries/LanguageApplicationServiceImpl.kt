package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.handlers.LanguageRefdataHandler
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.handlers.LanguageViewHandler
import net.nuclearprometheus.tpm.applicationserver.adapters.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Language
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service

@Service
class LanguageApplicationServiceImpl(
    private val languageViewHandler: LanguageViewHandler,
    private val languageRefdataHandler: LanguageRefdataHandler
    ) : LanguageApplicationService {

    private val logger = loggerFor(this::class.java)

    override fun getLanguages(query: FilteredRequest<Language>) =
        with(logger) {
            info("Language application service, method getLanguages")
            languageViewHandler.getLanguages(query)
        }

    override fun getLanguage(code: String) =
        with(logger) {
            info("Language application service, method getLanguage")
            languageViewHandler.getLanguage(code)
        }

    override fun getLanguageByNameLike(name: String) =
        with(logger) {
            info("Language application service, method getLanguageByNameLike")
            languageViewHandler.getLanguageByNameLike(name)
        }

    override fun getScopes() =
        with(logger) {
            info("Language application service, method getScopes")
            languageRefdataHandler.getScopes()
        }

    override fun getTypes() =
        with(logger) {
            info("Language application service, method getTypes")
            languageRefdataHandler.getTypes()
        }

}
package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.LanguageScopeView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.LanguageTypeView
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageScope
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageType
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service

@Service
class LanguageRefdataHandler {

    private val logger = loggerFor(this::class.java)

    fun getScopes(): List<LanguageScopeView> =
        with(logger) {
            info("Language refdata handler, method getScopes")
            LanguageScope.values().map { it.toView() }
        }

    fun getTypes(): List<LanguageTypeView> =
        with(logger) {
            info("Language refdata handler, method getTypes")
            LanguageType.values().map { it.toView() }
        }

}
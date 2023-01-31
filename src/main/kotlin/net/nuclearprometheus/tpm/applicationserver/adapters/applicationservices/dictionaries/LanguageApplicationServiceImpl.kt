package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.handlers.LanguageViewHandler
import org.springframework.stereotype.Service

@Service
class LanguageApplicationServiceImpl(private val languageViewHandler: LanguageViewHandler) : LanguageApplicationService {

    override fun getLanguages() = languageViewHandler.getLanguages()
    override fun getLanguage(code: String) = languageViewHandler.getLanguage(code)
    override fun getLanguageByNameLike(name: String) = languageViewHandler.getLanguageByNameLike(name)
}
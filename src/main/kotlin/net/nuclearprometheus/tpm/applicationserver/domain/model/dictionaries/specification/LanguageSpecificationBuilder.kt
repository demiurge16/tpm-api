package net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.specification

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Language
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageScope
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.LanguageType
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder

object LanguageSpecificationBuilder : SpecificationBuilder<Language>() {
    val code = uniqueValue("code", String::class)
    val iso6392t = uniqueValue("iso6392t", String::class)
    val iso6392b = uniqueValue("iso6392b", String::class)
    val iso6391 = uniqueValue("iso6391", String::class)
    val name = string("name")
    val scope = enum("scope", LanguageScope::class)
    val type = enum("type", LanguageType::class)
}
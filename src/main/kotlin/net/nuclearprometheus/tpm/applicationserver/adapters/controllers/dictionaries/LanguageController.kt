package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.LanguageApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.LanguageRequest
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/language")
class LanguageController(private val service: LanguageApplicationService) {

    private val logger = loggerFor(this::class.java)

    @GetMapping("")
    fun getAll(query: LanguageRequest.List) =
        with(logger) {
            info("GET /api/v1/language")
            service.getLanguages(query)
        }

    @GetMapping("/{code}")
    fun getByCode(@PathVariable(name = "code") code: String) =
        with(logger) {
            info("GET /api/v1/language/$code")
            service.getLanguage(code)
        }

    @GetMapping("/name/{name}")
    fun getByName(@PathVariable(name = "name") name: String) =
        with(logger) {
            info("GET /api/v1/language/name/$name")
            service.getLanguageByNameLike(name)
        }

    @GetMapping("/refdata/scopes")
    fun getScopes() =
        with(logger) {
            info("GET /api/v1/language/refdata/scopes")
            service.getLanguageScopes()
        }

    @GetMapping("/refdata/types")
    fun getTypes() =
        with(logger) {
            info("GET /api/v1/language/refdata/types")
            service.getLanguageTypes()
        }
}

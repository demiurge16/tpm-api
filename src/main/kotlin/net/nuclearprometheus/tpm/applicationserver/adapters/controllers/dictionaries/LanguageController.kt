package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.LanguageApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.LanguageListRequest
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
    fun getAll(query: LanguageListRequest) =
        with(logger) {
            info("Language controller, method getAll")
            service.getLanguages(query)
        }

    @GetMapping("/{code}")
    fun getByCode(@PathVariable(name = "code") code: String) =
        with(logger) {
            info("Language controller, method getByCode")
            service.getLanguage(code)
        }

    @GetMapping("/name/{name}")
    fun getByName(@PathVariable(name = "name") name: String) =
        with(logger) {
            info("Language controller, method getByName")
            service.getLanguageByNameLike(name)
        }

    @GetMapping("/refdata/scopes")
    fun getScopes() =
        with(logger) {
            info("Language controller, method getScopes")
            service.getScopes()
        }

    @GetMapping("/refdata/types")
    fun getTypes() =
        with(logger) {
            info("Language controller, method getTypes")
            service.getTypes()
        }
}

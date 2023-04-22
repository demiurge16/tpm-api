package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.dictionaries

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.LanguageApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.LanguageRequest
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.http.ResponseEntity
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

            ResponseEntity.ok().body(service.getLanguages(query))
        }

    @GetMapping("/{code}")
    fun getByCode(@PathVariable(name = "code") code: String) =
        with(logger) {
            info("GET /api/v1/language/$code")

            ResponseEntity.ok().body(service.getLanguage(code))
        }

    @GetMapping("/name/{name}")
    fun getByName(@PathVariable(name = "name") name: String) =
        with(logger) {
            info("GET /api/v1/language/name/$name")

            ResponseEntity.ok().body(service.getLanguageByNameLike(name))
        }

    @GetMapping("/refdata/scope")
    fun getScopes() =
        with(logger) {
            info("GET /api/v1/language/refdata/scope")

            ResponseEntity.ok().body(service.getLanguageScopes())
        }

    @GetMapping("/refdata/type")
    fun getTypes() =
        with(logger) {
            info("GET /api/v1/language/refdata/type")

            ResponseEntity.ok().body(service.getLanguageTypes())
        }
}

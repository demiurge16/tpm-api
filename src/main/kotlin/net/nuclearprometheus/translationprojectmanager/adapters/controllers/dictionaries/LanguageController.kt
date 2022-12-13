package net.nuclearprometheus.translationprojectmanager.adapters.controllers.dictionaries

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.dictionaries.LanguageApplicationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/language")
class LanguageController(private val service: LanguageApplicationService) {

    @GetMapping("")
    fun getAll() = service.getLanguages()

    @GetMapping("/{code}")
    fun getByCode(@PathVariable(name = "code") code: String) = service.getLanguage(code)

    @GetMapping("/name/{name}")
    fun getByName(@PathVariable(name = "name") name: String) = service.getLanguageByNameLike(name)
}

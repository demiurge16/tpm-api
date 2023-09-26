package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.dictionaries

import com.opencsv.bean.StatefulBeanToCsvBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.LanguageApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.requests.ListLanguages
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.responses.Language as LanguageResponse
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.OutputStreamWriter
import java.io.PipedInputStream
import java.io.PipedOutputStream
import java.time.LocalDate
import kotlin.concurrent.thread

@RestController
@RequestMapping("/api/v1/language")
class LanguageController(private val service: LanguageApplicationService) {

    private val logger = loggerFor(this::class.java)

    @GetMapping("")
    fun getAll(query: ListLanguages) =
        with(logger) {
            info("GET /api/v1/language")

            ResponseEntity.ok().body(service.getLanguages(query))
        }

    @GetMapping("/export", produces = ["text/csv"])
    fun export(query: ListLanguages) = with(logger) {
        info("GET /api/v1/language/export")

        val languages = service.getLanguages(query)

        val output = PipedOutputStream()
        val input = PipedInputStream(output)

        thread {
            val writer = OutputStreamWriter(output)
            val beanToCsv = StatefulBeanToCsvBuilder<LanguageResponse>(writer).build()
            beanToCsv.write(languages.items)
            writer.flush()
            writer.close()
            output.close()
        }

        val name = "languages-${LocalDate.now()}.csv"
        val resource = InputStreamResource(input)

        ResponseEntity.ok()
            .headers(
                HttpHeaders().apply {
                    add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=$name")
                    add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
                    add(HttpHeaders.PRAGMA, "no-cache")
                    add(HttpHeaders.EXPIRES, "0")
                }
            )
            .contentType(MediaType.parseMediaType("text/csv"))
            .body(resource)
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

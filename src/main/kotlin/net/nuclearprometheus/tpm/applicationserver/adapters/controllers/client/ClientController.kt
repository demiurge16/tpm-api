package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.client

import com.opencsv.bean.StatefulBeanToCsvBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.ClientApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.requests.ClientRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.client.responses.ClientResponse
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.OutputStreamWriter
import java.io.PipedInputStream
import java.io.PipedOutputStream
import java.time.LocalDate
import java.util.*
import kotlin.concurrent.thread

@RestController
@RequestMapping("/api/v1/client")
class ClientController(private val service: ClientApplicationService) {

    private val logger = loggerFor(this::class.java)

    @GetMapping("")
    fun getClients(query: ClientRequest.List) =
        with(logger) {
            info("GET /api/v1/client")

            ResponseEntity.ok().body(service.getClients(query))
        }

    @GetMapping("/export", produces = ["text/csv"])
    fun export(query: ClientRequest.List) = with(logger) {
        info("GET /api/v1/client/export")

        val accuracies = service.getClients(query)

        val output = PipedOutputStream()
        val input = PipedInputStream(output)

        thread {
            val writer = OutputStreamWriter(output)
            val beanToCsv = StatefulBeanToCsvBuilder<ClientResponse.Client>(writer).build()
            beanToCsv.write(accuracies.items)
            writer.flush()
            writer.close()
            output.close()
        }

        val name = "clients-${LocalDate.now()}.csv"

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

    @GetMapping("/{clientId}")
    fun getClientById(@PathVariable(name = "clientId") id: UUID) =
        with(logger) {
            info("GET /api/v1/client/$id")

            ResponseEntity.ok().body(service.getClient(id))
        }

    @PostMapping("")
    fun createClient(@RequestBody request: ClientRequest.Create) =
        with(logger) {
            info("POST /api/v1/client")

            ResponseEntity.ok().body(service.createClient(request))
        }

    @PutMapping("/{clientId}")
    fun updateClient(@PathVariable(name = "clientId") id: UUID, @RequestBody request: ClientRequest.Update) =
        with(logger) {
            info("PUT /api/v1/client/$id")

            ResponseEntity.ok().body(service.updateClient(id, request))
        }

    @PatchMapping("/{clientId}/activate")
    fun activate(@PathVariable(name = "clientId") id: UUID) =
        with(logger) {
            info("PATCH /api/v1/client/$id/activate")

            ResponseEntity.ok().body(service.activateClient(id))
        }

    @PatchMapping("/{clientId}/deactivate")
    fun deactivate(@PathVariable(name = "clientId") id: UUID) =
        with(logger) {
            info("PATCH /api/v1/client/$id/deactivate")

            ResponseEntity.ok().body(service.deactivateClient(id))
        }
}

package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.user

import com.opencsv.bean.StatefulBeanToCsvBuilder
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.UserApplicationService
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.requests.UserRequest
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.responses.UserResponse
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
import java.util.*
import kotlin.concurrent.thread

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val service: UserApplicationService
) {

    private val logger = loggerFor(UserController::class.java)

    @GetMapping("")
    fun getUsers(query: UserRequest.List) = with(logger) {
        info("GET /api/v1/user")
        ResponseEntity.ok().body(service.getUsers(query))
    }

    @GetMapping("/export", produces = ["text/csv"])
    fun export(query: UserRequest.List) = with(logger) {
        info("GET /api/v1/project/export")

        val files = service.getUsers(query)

        val output = PipedOutputStream()
        val input = PipedInputStream(output)

        thread {
            val writer = OutputStreamWriter(output)
            val beanToCsv = StatefulBeanToCsvBuilder<UserResponse.User>(writer).build()
            beanToCsv.write(files.items)
            writer.flush()
            writer.close()
            output.close()
        }

        val name = "users-${LocalDate.now()}.csv"
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

    @GetMapping("/{userId}")
    fun getUser(@PathVariable(name = "userId") userId: UUID) = with(logger) {
        info("GET /api/v1/user/$userId")
        ResponseEntity.ok().body(service.getUser(userId))
    }

    @GetMapping("/current")
    fun getCurrentUser() = with(logger) {
        info("GET /api/v1/user/current")
        ResponseEntity.ok().body(service.getCurrentUser())
    }
}


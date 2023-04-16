package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.user

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.UserApplicationService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userApplicationService: UserApplicationService
) {

    private val logger = loggerFor(UserController::class.java)

    @GetMapping("")
    fun getUsers() = with(logger) {
        info("GET /api/v1/user")

        userApplicationService.getUsers()
    }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable(name = "userId") userId: UUID) = with(logger) {
        info("GET /api/v1/user/$userId")

        userApplicationService.getUser(userId)
    }

    @GetMapping("/current")
    fun getCurrentUser() = with(logger) {
        info("GET /api/v1/user/current")

        userApplicationService.getCurrentUser()
    }
}


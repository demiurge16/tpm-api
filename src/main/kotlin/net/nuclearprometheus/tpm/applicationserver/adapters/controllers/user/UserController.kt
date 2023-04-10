package net.nuclearprometheus.tpm.applicationserver.adapters.controllers.user

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/user")
class UserController {

    @GetMapping("")
    fun getUsers() {
        TODO()
    }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable(name = "userId") userId: UUID) {
        TODO()
    }

    @GetMapping("/current")
    fun getCurrentUser() {
        TODO()
    }
}

package net.nuclearprometheus.tpm.applicationserver.domain.model.user.specification

import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserRole
import net.nuclearprometheus.tpm.applicationserver.domain.queries.specification.dsl.SpecificationBuilder
import java.util.UUID

object UserSpecificationBuilder : SpecificationBuilder<User>() {
    val id = uniqueValue("id", UUID::class)
    val firstName = string("firstName")
    val lastName = string("lastName")
    val username = string("username")
    val email = string("email")
    val roles = collection("roles", UserRole::class)
}

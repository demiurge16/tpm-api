package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.responses.Role
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.responses.UserResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserRole

object UserMapper {
    fun User.toView(): UserResponse.User {
        return UserResponse.User(
            id = id.value,
            firstName = firstName,
            lastName = lastName,
            username = username,
            email = email,
            roles = roles.map {
                Role(
                    role = it,
                    tag = it.role,
                    title = it.title,
                    description = it.description
                )
            }
        )
    }
}
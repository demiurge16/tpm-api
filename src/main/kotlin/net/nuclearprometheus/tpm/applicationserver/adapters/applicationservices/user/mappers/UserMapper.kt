package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.responses.UserResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User

object UserMapper {
    fun User.toView(): UserResponse.View {
        return UserResponse.View(
            id = id.value,
            firstName = firstName,
            lastName = lastName,
            username = username,
            email = email
        )
    }
}
package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user

import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User

interface UserContextProvider {
    fun getCurrentUser(): User
}

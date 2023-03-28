package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user

import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId

interface UserService {

    fun getAll(): List<User>
    fun get(id: UserId): User
    fun get(username: String): User
}

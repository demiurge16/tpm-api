package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user

import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId

interface UserRepository {
    fun getAll(): List<User>
    fun get(id: UserId): User?
    fun getByUsername(username: String): User?
    fun getByEmail(email: String): User?
}

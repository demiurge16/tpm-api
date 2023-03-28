package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user

import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId

interface UserRepository {
    fun findAll(): List<User>
    fun find(id: UserId): User?
    fun findByUsername(username: String): User?
    fun findByEmail(email: String): User?
}

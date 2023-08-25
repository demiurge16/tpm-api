package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user

import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page

interface UserRepository {
    fun getAll(): List<User>
    fun get(id: UserId): User?
    fun get(query: Query<User>): Page<User>
    fun getByUsername(username: String): User?
    fun getByEmail(email: String): User?
}

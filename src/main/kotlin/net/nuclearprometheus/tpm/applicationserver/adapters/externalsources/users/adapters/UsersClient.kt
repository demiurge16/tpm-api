package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.users.adapters

import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import org.keycloak.admin.client.Keycloak
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class UsersClient(
    private val keycloakClient: Keycloak,
    @Value("\${keycloak.realm}") private val realm: String
) : UserRepository {

    override fun getAll() = keycloakClient.realm(realm)
        .users()
        .list()
        .map {
            User(
                id = UserId(UUID.fromString(it.id)),
                username = it.username,
                email = it.email,
                firstName = it.firstName,
                lastName = it.lastName
            )
        }

    override fun get(id: UserId) = keycloakClient.realm(realm)
        .users()
        .get(id.value.toString())
        .toRepresentation()
        .let {
            User(
                id = UserId(UUID.fromString(it.id)),
                username = it.username,
                email = it.email,
                firstName = it.firstName,
                lastName = it.lastName
            )
        }

    override fun getByUsername(username: String) = keycloakClient.realm(realm)
        .users()
        .searchByUsername(username, true)
        .firstOrNull()
        ?.let {
            User(
                id = UserId(UUID.fromString(it.id)),
                username = it.username,
                email = it.email,
                firstName = it.firstName,
                lastName = it.lastName
            )
        }

    override fun getByEmail(email: String) = keycloakClient.realm(realm)
        .users()
        .searchByEmail(email, true)
        .firstOrNull()
        ?.let {
            User(
                id = UserId(UUID.fromString(it.id)),
                username = it.username,
                email = it.email,
                firstName = it.firstName,
                lastName = it.lastName
            )
        }
}

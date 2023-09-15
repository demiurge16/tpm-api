package net.nuclearprometheus.tpm.applicationserver.adapters.externalsources.users.adapters

import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserRole
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.queries.Query
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.singlePage
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.keycloak.admin.client.Keycloak
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UsersClient(
    private val keycloakClient: Keycloak,
    @Value("\${app.keycloak.realm}") private val realm: String
) : UserRepository {

    private val logger = loggerFor(this::class.java)

    override fun getAll() = keycloakClient.realm(realm)
        .users()
        .list()
        .map {
            User(
                id = UserId(UUID.fromString(it.id)),
                username = it.username,
                email = it.email,
                firstName = it.firstName,
                lastName = it.lastName,
                roles = keycloakClient.realm(realm)
                    .users()
                    .get(it.id)
                    .roles()
                    .realmLevel()
                    .listEffective()
                    .mapNotNull { role -> role.name.toUserRole() }
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
                lastName = it.lastName,
                roles = keycloakClient.realm(realm)
                    .users()
                    .get(it.id)
                    .roles()
                    .realmLevel()
                    .listEffective()
                    .mapNotNull { role -> role.name.toUserRole() }
            )
        }

    override fun get(query: Query<User>): Page<User> {
        return singlePage(getAll())
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
                lastName = it.lastName,
                roles = keycloakClient.realm(realm)
                    .users()
                    .get(it.id)
                    .roles()
                    .realmLevel()
                    .listEffective()
                    .mapNotNull { role -> role.name.toUserRole() }
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
                lastName = it.lastName,
                roles = keycloakClient.realm(realm)
                    .users()
                    .get(it.id)
                    .roles()
                    .realmLevel()
                    .listEffective()
                    .mapNotNull { role -> role.name.toUserRole() }
            )
        }

    private fun String.toUserRole() =
        when (this) {
            "admin" -> UserRole.ADMIN
            "project-manager" -> UserRole.PROJECT_MANAGER
            "translator" -> UserRole.TRANSLATOR
            "editor" -> UserRole.EDITOR
            "proofreader" -> UserRole.PROOFREADER
            "subject-matter-expert" -> UserRole.SUBJECT_MATTER_EXPERT
            "publisher" -> UserRole.PUBLISHER
            "observer" -> UserRole.OBSERVER
            "user" -> UserRole.USER
            else -> null
        }
}

package net.nuclearprometheus.tpm.applicationserver.domain.model.user

/**
 * User model
 * Serves only as a marker type for audit purposes. Actual user handling is done by Spring Security and Keycloak.
 *
 * @param id user id
 * @param firstName user first name
 * @param lastName user last name
 * @param username user username
 * @param email user email
 */
class User(
    val id: UserId = UserId(),
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String
)

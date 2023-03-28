package net.nuclearprometheus.tpm.applicationserver.domain.model.user

/**
 * User model
 * Serves only as a marker type for audit purposes. Actual user handling is done by Spring Security and Keycloak.
 *
 * @param id user id
 * @param firstName user first name
 * @param lastName user last name
 * @param email user email
 */
class User(
    val id: UserId = UserId(),
    firstName: String,
    lastName: String,
    email: String
) {
    var firstName = firstName; private set
    var lastName = lastName; private set
    var email = email; private set
}

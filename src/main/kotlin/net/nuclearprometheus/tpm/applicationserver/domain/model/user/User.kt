package net.nuclearprometheus.tpm.applicationserver.domain.model.user

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity

class User(
    id: UserId = UserId(),
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val roles: List<UserRole>
) : Entity<UserId>(id)

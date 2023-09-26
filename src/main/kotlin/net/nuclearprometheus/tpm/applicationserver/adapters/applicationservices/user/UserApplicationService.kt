package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.mappers.UserMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.responses.User as UserResponse
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.common.requests.FilteredRequest
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider
import net.nuclearprometheus.tpm.applicationserver.domain.queries.pagination.Page
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserApplicationService(
    private val userRepository: UserRepository,
    private val userContextProvider: UserContextProvider
) {

    private val logger = loggerFor(UserApplicationService::class.java)

    fun getUsers(query: FilteredRequest<User>): Page<UserResponse> {
        logger.info("getUsers($query)")
        return userRepository.get(query.toQuery()).map { it.toView() }
    }

    fun getUser(userId: UUID): UserResponse {
        logger.info("getUser($userId)")
        return userRepository.get(UserId(userId))?.toView() ?: throw NotFoundException("User with id $userId not found")
    }

    fun getCurrentUser(): UserResponse {
        logger.info("getCurrentUser()")
        return userContextProvider.getCurrentUser().toView()
    }
}
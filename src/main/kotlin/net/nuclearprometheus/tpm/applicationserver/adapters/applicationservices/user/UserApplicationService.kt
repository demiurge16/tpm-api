package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user

import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.singlePage
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.mappers.UserMapper.toView
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserApplicationService(
    private val userRepository: UserRepository,
    private val userContextProvider: UserContextProvider
) {

    private val logger = loggerFor(UserApplicationService::class.java)

    fun getUsers() = with(logger) {
        info("getUsers()")

        singlePage(userRepository.getAll()).map { it.toView() }
    }

    fun getUser(userId: UUID) = with(logger) {
        info("getUser($userId)")

        userRepository.get(UserId(userId))?.toView() ?: throw NotFoundException("User with id $userId not found")
    }

    fun getCurrentUser() = with(logger) {
        info("getCurrentUser()")

        userContextProvider.getCurrentUser().toView()
    }
}
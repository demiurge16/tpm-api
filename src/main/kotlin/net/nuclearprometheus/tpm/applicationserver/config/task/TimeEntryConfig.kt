package net.nuclearprometheus.tpm.applicationserver.config.task

import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.specification.TimeEntrySpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.task.TaskRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.task.TimeEntryRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.task.TimeEntryService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.task.TimeEntryServiceImpl
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TimeEntryConfig(
    private val timeEntryRepository: TimeEntryRepository,
    private val userRepository: UserRepository,
    private val userContextProvider: UserContextProvider,
    private val projectRepository: ProjectRepository,
    private val taskRepository: TaskRepository
) {

    @Bean
    fun timeEntryService(): TimeEntryService =
        TimeEntryServiceImpl(
            timeEntryRepository = timeEntryRepository,
            userRepository = userRepository,
            userContextProvider = userContextProvider,
            projectRepository = projectRepository,
            taskRepository = taskRepository,
            logger = loggerFor(TimeEntryService::class.java)
        )

    @Bean
    fun timeEntrySpecificationBuilder() = TimeEntrySpecificationBuilder
}
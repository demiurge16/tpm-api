package net.nuclearprometheus.tpm.applicationserver.config.task

import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.task.TaskRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.task.TaskService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.task.TaskServiceImpl
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.context.annotation.Configuration

@Configuration
class TaskConfig(
    private val taskRepository: TaskRepository,
    private val languageRepository: LanguageRepository,
    private val accuracyRepository: AccuracyRepository,
    private val industryRepository: IndustryRepository,
    private val unitRepository: UnitRepository,
    private val currencyRepository: CurrencyRepository,
    private val priorityRepository: PriorityRepository,
    private val projectRepository: ProjectRepository,
    private val teamMemberRepository: TeamMemberRepository
) {

    private val logger = loggerFor(TaskService::class.java)

    fun taskService(): TaskService =
        TaskServiceImpl(
            taskRepository,
            languageRepository,
            accuracyRepository,
            industryRepository,
            unitRepository,
            currencyRepository,
            priorityRepository,
            projectRepository,
            teamMemberRepository,
            logger
        )
}
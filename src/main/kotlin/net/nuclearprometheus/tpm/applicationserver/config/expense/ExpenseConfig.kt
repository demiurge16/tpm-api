package net.nuclearprometheus.tpm.applicationserver.config.expense

import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.expense.ExpenseRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.expense.ExpenseService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.expense.ExpenseServiceImpl
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ExpenseConfig(
    private val expenseRepository: ExpenseRepository,
    private val projectRepository: ProjectRepository,
    private val teamMemberRepository: TeamMemberRepository
) {

    private val logger = loggerFor(ExpenseService::class.java)

    @Bean
    fun expenseService(): ExpenseService = ExpenseServiceImpl(expenseRepository, projectRepository, teamMemberRepository, logger)
}

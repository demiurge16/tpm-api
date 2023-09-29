package net.nuclearprometheus.tpm.applicationserver.config.expense

import net.nuclearprometheus.tpm.applicationserver.config.security.PolicyEnforcerPathsProvider
import net.nuclearprometheus.tpm.applicationserver.config.security.methodConfig
import net.nuclearprometheus.tpm.applicationserver.config.security.pathConfig
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.CurrencyRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.ExpenseCategoryRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.expense.ExpenseRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.expense.ExpenseService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.expense.ExpenseServiceImpl
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ExpenseConfig(
    private val expenseRepository: ExpenseRepository,
    private val projectRepository: ProjectRepository,
    private val userRepository: UserRepository,
    private val expenseCategoryRepository: ExpenseCategoryRepository,
    private val currencyRepository: CurrencyRepository,
    private val userContextProvider: UserContextProvider
) {

    @Bean
    fun expenseService(): ExpenseService =
        ExpenseServiceImpl(
            expenseRepository,
            projectRepository,
            userRepository,
            expenseCategoryRepository,
            currencyRepository,
            userContextProvider,
            loggerFor(ExpenseService::class.java)
        )

    @Bean
    fun expensePolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/expense"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:expense:query")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/expense/export"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:expense:export")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/expense/{expenseId}"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:expense:read")
                    },
                    methodConfig {
                        method = "DELETE"
                        scopes = mutableListOf("urn:tpm-backend:resource:expense:delete")
                    }
                )
            }
        )
    }
}

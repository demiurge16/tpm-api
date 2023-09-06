package net.nuclearprometheus.tpm.applicationserver.config.dictionaries

import net.nuclearprometheus.tpm.applicationserver.config.security.PolicyEnforcerPathsProvider
import net.nuclearprometheus.tpm.applicationserver.config.security.methodConfig
import net.nuclearprometheus.tpm.applicationserver.config.security.pathConfig
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.dictionaries.ExpenseCategoryRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.ExpenseCategoryService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.dictionaries.ExpenseCategoryServiceImpl
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ExpenseCategoryConfig(private val expenseCategoryRepository: ExpenseCategoryRepository) {

    @Bean
    fun expenseCategoryService(): ExpenseCategoryService = ExpenseCategoryServiceImpl(
        expenseCategoryRepository,
        loggerFor(ExpenseCategoryService::class.java)
    )

    @Bean
    fun expenseCategoryPolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/expense-category"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:expense-category:query")
                    },
                    methodConfig {
                        method = "POST"
                        scopes = mutableListOf("tpm-backend:expense-category:create")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/expense-category/export"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:expense-category:export")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/expense-category/{expenseCategoryId}"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("tpm-backend:expense-category:read")
                    },
                    methodConfig {
                        method = "PUT"
                        scopes = mutableListOf("tpm-backend:expense-category:update")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/expense-category/{expenseCategoryId}/*"
                methods = mutableListOf(
                    methodConfig {
                        method = "PATCH"
                        scopes = mutableListOf("tpm-backend:expense-category:update")
                    }
                )
            },
        )
    }
}
package net.nuclearprometheus.tpm.applicationserver.config.files

import net.nuclearprometheus.tpm.applicationserver.config.security.PolicyEnforcerPathsProvider
import net.nuclearprometheus.tpm.applicationserver.config.security.methodConfig
import net.nuclearprometheus.tpm.applicationserver.config.security.pathConfig
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.file.FileRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.file.FileService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.file.FileServiceImpl
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import net.nuclearprometheus.tpm.applicationserver.domain.model.file.specification.FileSpecificationBuilder
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.user.UserContextProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FileConfig(
    private val fileRepository: FileRepository,
    private val projectRepository: ProjectRepository,
    private val userContextProvider: UserContextProvider
) {

    @Bean
    fun fileService(): FileService = FileServiceImpl(
        fileRepository,
        projectRepository,
        userContextProvider,
        loggerFor(FileService::class.java)
    )

    @Bean
    fun fileSpecificationBuilder() = FileSpecificationBuilder

    @Bean
    fun filePolicyEnforcerPathsProvider() = object : PolicyEnforcerPathsProvider {
        override val paths = mutableListOf(
            pathConfig {
                path = "/api/v1/file"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:file:query")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/file/export"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:file:export")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/file/{fileId}"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:file:read")
                    },
                    methodConfig {
                        method = "DELETE"
                        scopes = mutableListOf("urn:tpm-backend:resource:file:delete")
                    }
                )
            },
            pathConfig {
                path = "/api/v1/file/{fileId}/download"
                methods = mutableListOf(
                    methodConfig {
                        method = "GET"
                        scopes = mutableListOf("urn:tpm-backend:resource:file:download")
                    }
                )
            }
        )
    }
}

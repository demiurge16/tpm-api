package net.nuclearprometheus.tpm.applicationserver.config.files

import net.nuclearprometheus.tpm.applicationserver.config.security.PolicyEnforcerPathsProvider
import net.nuclearprometheus.tpm.applicationserver.config.security.methodConfig
import net.nuclearprometheus.tpm.applicationserver.config.security.pathConfig
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.file.FileRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.user.UserRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.file.FileService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.file.FileServiceImpl
import net.nuclearprometheus.tpm.applicationserver.config.logging.loggerFor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FileConfig(
    private val fileRepository: FileRepository,
    private val teamMemberRepository: TeamMemberRepository,
    private val userRepository: UserRepository,
    private val projectRepository: ProjectRepository,
) {

    @Bean
    fun fileService(): FileService = FileServiceImpl(
        fileRepository,
        teamMemberRepository,
        userRepository,
        projectRepository,
        loggerFor(FileService::class.java)
    )

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

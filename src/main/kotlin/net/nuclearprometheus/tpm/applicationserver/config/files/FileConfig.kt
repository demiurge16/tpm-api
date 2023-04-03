package net.nuclearprometheus.tpm.applicationserver.config.files

import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.file.FileRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.file.FileService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.file.FileServiceImpl
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FileConfig(
    private val fileRepository: FileRepository,
    private val teamMemberRepository: TeamMemberRepository,
    private val projectRepository: ProjectRepository,
) {

    private val logger = loggerFor(FileService::class.java)

    @Bean
    fun fileService(): FileService = FileServiceImpl(fileRepository, teamMemberRepository, projectRepository, logger)

}

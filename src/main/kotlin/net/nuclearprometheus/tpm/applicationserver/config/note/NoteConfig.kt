package net.nuclearprometheus.tpm.applicationserver.config.note

import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.note.NoteRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.note.NoteService
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.note.NoteServiceImpl
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class NoteConfig(
    private val noteRepository: NoteRepository,
    private val teamMemberRepository: TeamMemberRepository,
    private val projectRepository: ProjectRepository
) {

    private val logger = loggerFor(NoteService::class.java)

    @Bean
    fun noteService(): NoteService = NoteServiceImpl(noteRepository, teamMemberRepository, projectRepository, logger)
}
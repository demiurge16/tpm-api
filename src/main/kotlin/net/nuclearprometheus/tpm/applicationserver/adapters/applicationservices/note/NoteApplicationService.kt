package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.note

import net.nuclearprometheus.tpm.applicationserver.adapters.common.responses.singlePage
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.note.mappers.NoteMapper.toView
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.NotFoundException
import net.nuclearprometheus.tpm.applicationserver.domain.model.note.NoteId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.note.NoteRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.project.ProjectRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.note.NoteService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import java.util.*

@Service
class NoteApplicationService(
    private val repository: NoteRepository,
    private val service: NoteService,
    private val projectRepository: ProjectRepository
) {

    private val logger = loggerFor(NoteApplicationService::class.java)

    fun getNotes() = with(logger) {
        info("getNotes()")

        singlePage(repository.getAll()).map {
            val project = projectRepository.get(it.projectId)
                ?: throw IllegalStateException("Project with id ${it.projectId} not found")

            it.toView(project)
        }
    }

    fun getNote(id: UUID) = with(logger) {
        info("getNote($id)")

        repository.get(NoteId(id))?.let {
            val project = projectRepository.get(it.projectId)
                ?: throw IllegalStateException("Project with id ${it.projectId} not found")

            it.toView(project)
        } ?: throw NotFoundException("Note with id $id not found")
    }

    fun deleteNote(id : UUID) = with(logger) {
        info("deleteNote($id)")

        service.delete(NoteId(id))
    }
}
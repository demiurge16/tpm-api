package net.nuclearprometheus.tpm.applicationserver.adapters.persistence.note.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.note.entities.NoteDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.note.repositories.NoteJpaRepository
import net.nuclearprometheus.tpm.applicationserver.adapters.persistence.teammember.adapters.TeamMemberRepositoryImpl.Mappers.toDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.domain.model.note.Note
import net.nuclearprometheus.tpm.applicationserver.domain.model.note.NoteId
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.note.NoteRepository
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.teammember.TeamMemberRepository
import org.springframework.stereotype.Repository

@Repository
class NoteRepositoryImpl(
    private val jpaRepository: NoteJpaRepository,
    private val teamMemberRepository: TeamMemberRepository
) : NoteRepository {

    override fun getAll() = jpaRepository.findAll().map { it.toDomain(teamMemberRepository) }
    override fun get(id: NoteId): Note? = jpaRepository.findById(id.value).map { it.toDomain(teamMemberRepository) }.orElse(null)
    override fun get(ids: List<NoteId>) = jpaRepository.findAllById(ids.map { it.value }).map { it.toDomain(teamMemberRepository) }
    override fun create(entity: Note) = jpaRepository.save(entity.toDatabaseModel(teamMemberRepository)).toDomain(teamMemberRepository)
    override fun createAll(entities: List<Note>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel(teamMemberRepository) }).map { it.toDomain(teamMemberRepository) }
    override fun update(entity: Note) = jpaRepository.save(entity.toDatabaseModel(teamMemberRepository)).toDomain(teamMemberRepository)
    override fun updateAll(entities: List<Note>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel(teamMemberRepository) }).map { it.toDomain(teamMemberRepository) }
    override fun delete(id: NoteId) = jpaRepository.deleteById(id.value)
    override fun getAllByProjectId(projectId: ProjectId) = jpaRepository.findAllByProjectId(projectId.value).map { it.toDomain(teamMemberRepository) }
    override fun deleteAll(ids: List<NoteId>) = jpaRepository.deleteAllById(ids.map { it.value })
    override fun deleteAllByProjectId(projectId: ProjectId) = jpaRepository.deleteAllByProjectId(projectId.value)

    companion object Mappers {
        fun NoteDatabaseModel.toDomain(teamMemberRepository: TeamMemberRepository) = Note(
            id = NoteId(id),
            content = content,
            author = TeamMemberId(author.id).let {
                teamMemberRepository.get(it) ?: throw IllegalArgumentException("Author not found")
            },
            createdAt = createdAt,
            projectId = ProjectId(projectId)
        )

        fun Note.toDatabaseModel(teamMemberRepository: TeamMemberRepository) = NoteDatabaseModel(
            id = id.value,
            content = content,
            author = teamMemberRepository.get(author.id)?.toDatabaseModel() ?: throw IllegalArgumentException("Author not found"),
            createdAt = createdAt,
            projectId = projectId.value
        )
    }
}
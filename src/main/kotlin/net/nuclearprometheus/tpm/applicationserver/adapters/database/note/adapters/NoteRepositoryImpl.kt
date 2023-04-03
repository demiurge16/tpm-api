package net.nuclearprometheus.tpm.applicationserver.adapters.database.note.adapters

import net.nuclearprometheus.tpm.applicationserver.adapters.database.note.entities.NoteDatabaseModel
import net.nuclearprometheus.tpm.applicationserver.adapters.database.note.repositories.NoteJpaRepository
import net.nuclearprometheus.tpm.applicationserver.adapters.database.teammember.adapters.TeamMemberRepositoryImpl.Mappers.toDatabaseModel
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

    override fun getAll() = jpaRepository.findAll().map { it.toDomain() }
    override fun get(id: NoteId): Note? = jpaRepository.findById(id.value).map { it.toDomain() }.orElse(null)
    override fun get(ids: List<NoteId>) = jpaRepository.findAllById(ids.map { it.value }).map { it.toDomain() }
    override fun create(entity: Note) = jpaRepository.save(entity.toDatabaseModel(teamMemberRepository)).toDomain()
    override fun createAll(entities: List<Note>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel(teamMemberRepository) }).map { it.toDomain() }
    override fun update(entity: Note) = jpaRepository.save(entity.toDatabaseModel(teamMemberRepository)).toDomain()
    override fun updateAll(entities: List<Note>) = jpaRepository.saveAll(entities.map { it.toDatabaseModel(teamMemberRepository) }).map { it.toDomain() }
    override fun delete(id: NoteId) = jpaRepository.deleteById(id.value)
    override fun getAllByProjectId(projectId: ProjectId) = jpaRepository.findAllByProjectId(projectId.value).map { it.toDomain() }
    override fun deleteAll(ids: List<NoteId>) = jpaRepository.deleteAllById(ids.map { it.value })

    companion object Mappers {
        fun NoteDatabaseModel.toDomain() = Note(
            id = NoteId(id),
            content = content,
            authorId = TeamMemberId(author.id),
            createdAt = createdAt,
            projectId = ProjectId(projectId)
        )

        fun Note.toDatabaseModel(teamMemberRepository: TeamMemberRepository) = NoteDatabaseModel(
            id = id.value,
            content = content,
            author = teamMemberRepository.get(authorId)?.toDatabaseModel() ?: throw IllegalArgumentException("Author not found"),
            createdAt = createdAt,
            projectId = projectId.value
        )
    }
}
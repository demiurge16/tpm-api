package net.nuclearprometheus.tpm.applicationserver.domain.model.note

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMember
import java.time.ZonedDateTime

class Note(
    id: NoteId = NoteId(),
    val content: String,
    val author: TeamMember,
    val createdAt: ZonedDateTime = ZonedDateTime.now(),
    val projectId: ProjectId
) : Entity<NoteId>(id)

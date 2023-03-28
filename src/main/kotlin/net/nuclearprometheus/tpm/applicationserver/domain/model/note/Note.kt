package net.nuclearprometheus.tpm.applicationserver.domain.model.note

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import java.time.ZonedDateTime

class Note(
    val id: NoteId = NoteId(),
    val content: String,
    val authorId: TeamMemberId,
    val createdAt: ZonedDateTime = ZonedDateTime.now(),
    val projectId: ProjectId
)

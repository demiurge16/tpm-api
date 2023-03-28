package net.nuclearprometheus.tpm.applicationserver.domain.model.note

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User
import java.time.ZonedDateTime

class Note(
    val id: NoteId = NoteId(),
    val content: String,
    val author: User,
    val createdAt: ZonedDateTime = ZonedDateTime.now(),
    val projectId: ProjectId
)

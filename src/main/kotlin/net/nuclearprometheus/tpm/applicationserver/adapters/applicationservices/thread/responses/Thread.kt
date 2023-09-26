package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.responses

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectShortView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.responses.User
import java.time.ZonedDateTime
import java.util.*

data class Thread(
    val id: UUID,
    val title: String,
    val content: String,
    val author: User,
    val createdAt: ZonedDateTime,
    val project: ProjectShortView,
    val status: ThreadStatus,
    val likes: List<Like>,
    val dislikes: List<Dislike>,
    val tags: List<Tag>
)

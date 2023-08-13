package net.nuclearprometheus.tpm.applicationserver.domain.model.thread

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.thread.ThreadStatusChangeException
import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User
import java.time.ZonedDateTime

class Thread(
    id: ThreadId = ThreadId(),
    title: String,
    content: String,
    author: User,
    createdAt: ZonedDateTime = ZonedDateTime.now(),
    status: ThreadStatus = ThreadStatus.DRAFT,
    replies: List<Reply> = mutableListOf(),
    threadLikes: List<ThreadLike> = mutableListOf(),
    threadDislikes: List<ThreadDislike> = mutableListOf(),
    tags: List<Tag> = mutableListOf(),
    projectId: ProjectId
): Entity<ThreadId>(id) {

    var title = title; private set
    var content = content; private set
    var author = author; private set
    var createdAt = createdAt; private set
    var status = status; private set
    var replies = replies; private set
    var likes = threadLikes; private set
    var dislikes = threadDislikes; private set
    var tags = tags; private set
    var projectId = projectId; private set

    fun update(title: String, content: String, tags: List<Tag>) {
        this.title = title
        this.content = content
        this.tags = tags
    }

    fun addLike(like : ThreadLike) {
        this.likes = this.likes + like
    }

    fun addDislike(dislike : ThreadDislike) {
        this.dislikes = this.dislikes + dislike
    }

    fun activate() {
        if (status !in listOf(ThreadStatus.DRAFT, ThreadStatus.FREEZE, ThreadStatus.CLOSED)) {
            throw ThreadStatusChangeException("Thread can be activated only from DRAFT, FREEZE or CLOSED status")
        }

        this.status = ThreadStatus.ACTIVE
    }

    fun freeze() {
        if (status !in listOf(ThreadStatus.ACTIVE)) {
            throw ThreadStatusChangeException("Thread can be freezed only from ACTIVEstatus")
        }

        this.status = ThreadStatus.FREEZE
    }

    fun close() {
        if (status !in listOf(ThreadStatus.ACTIVE, ThreadStatus.FREEZE)) {
            throw ThreadStatusChangeException("Thread can be closed only from ACTIVE or FREEZE status")
        }

        this.status = ThreadStatus.CLOSED
    }

    fun archive() {
        if (status !in listOf(ThreadStatus.CLOSED)) {
            throw ThreadStatusChangeException("Thread can be archived only from CLOSED status")
        }

        this.status = ThreadStatus.ARCHIVED
    }

    fun delete() {
        this.status = ThreadStatus.DELETED
    }
}


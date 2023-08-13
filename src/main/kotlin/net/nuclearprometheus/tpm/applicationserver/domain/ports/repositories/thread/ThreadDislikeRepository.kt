package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread

import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadDislike
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadDislikeId
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository

interface ThreadDislikeRepository : BaseRepository<ThreadDislike, ThreadDislikeId> {
    fun getAllByThreadId(threadId: ThreadId): List<ThreadDislike>
    fun getByThreadIdAndAuthorId(threadId: ThreadId, authorId: UserId): ThreadDislike?
    fun deleteByThreadIdAndAuthorId(threadId: ThreadId, authorId: UserId)
}
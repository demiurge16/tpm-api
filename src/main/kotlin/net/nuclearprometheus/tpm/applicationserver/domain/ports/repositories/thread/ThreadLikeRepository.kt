package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread

import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadId
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadLike
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadLikeId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository

interface ThreadLikeRepository : BaseRepository<ThreadLike, ThreadLikeId> {
    fun getAllByThreadId(threadId: ThreadId): List<ThreadLike>
    fun getByThreadIdAndAuthorId(threadId: ThreadId, authorId: UserId): ThreadLike?
    fun deleteByThreadIdAndAuthorId(threadId: ThreadId, authorId: UserId)
}

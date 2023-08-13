package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread

import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Reply
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ReplyId
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository

interface ReplyRepository : BaseRepository<Reply, ReplyId> {

    fun getAllByThreadId(threadId: ThreadId): List<Reply>
}
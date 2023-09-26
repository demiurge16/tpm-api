package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.thread

import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Reply
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ReplyId
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId

interface ReplyService {

    fun create(content: String, threadId: ThreadId, parentReplyId: ReplyId? = null): Reply
    fun update(id: ReplyId, content: String): Reply
    fun like(id: ReplyId): Reply
    fun unlike(id: ReplyId): Reply
    fun dislike(id: ReplyId): Reply
    fun undislike(id: ReplyId): Reply
    fun delete(id: ReplyId)
}
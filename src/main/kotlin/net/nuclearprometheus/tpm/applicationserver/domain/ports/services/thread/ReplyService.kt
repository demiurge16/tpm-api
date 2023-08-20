package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.thread

import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Reply
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ReplyId
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId

interface ReplyService {

    fun create(content: String, authorId: UserId, threadId: ThreadId, parentReplyId: ReplyId? = null): Reply
    fun update(id: ReplyId, content: String): Reply
    fun like(id: ReplyId, authorId: UserId): Reply
    fun unlike(id: ReplyId, authorId: UserId): Reply
    fun dislike(id: ReplyId, authorId: UserId): Reply
    fun undislike(id: ReplyId, authorId: UserId): Reply
    fun delete(id: ReplyId)
}
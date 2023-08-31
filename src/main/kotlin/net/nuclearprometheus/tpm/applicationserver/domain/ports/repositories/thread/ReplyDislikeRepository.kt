package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread

import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ReplyDislike
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ReplyDislikeId
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ReplyId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository

interface ReplyDislikeRepository : BaseRepository<ReplyDislike, ReplyDislikeId> {
    fun getAllByReplyId(replyId: ReplyId): List<ReplyDislike>
    fun getByReplyIdAndUserId(replyId: ReplyId, userId: UserId): ReplyDislike?
    fun deleteByReplyIdAndUserId(replyId: ReplyId, userId: UserId)
}

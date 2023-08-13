package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread

import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ReplyId
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ReplyLike
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ReplyLikeId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository

interface ReplyLikeRepository : BaseRepository<ReplyLike, ReplyLikeId> {
    fun getAllByReplyId(replyId: ReplyId): List<ReplyLike>
    fun getByReplyIdAndUserId(replyId: ReplyId, userId: UserId): ReplyLike?
    fun deleteByReplyIdAndUserId(replyId: ReplyId, userId: UserId)
}
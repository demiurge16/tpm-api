package net.nuclearprometheus.tpm.applicationserver.domain.model.thread

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User
import java.time.ZonedDateTime

class ReplyLike(
    id: ReplyLikeId = ReplyLikeId(),
    author: User,
    createdAt: ZonedDateTime = ZonedDateTime.now(),
    replyId: ReplyId
) : Entity<ReplyLikeId>(id) {

    var author = author; private set
    var createdAt = createdAt; private set
    var replyId = replyId; private set
}
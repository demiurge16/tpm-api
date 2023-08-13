package net.nuclearprometheus.tpm.applicationserver.domain.model.thread

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.User
import java.time.ZonedDateTime

class ThreadDislike(
    id: ThreadDislikeId = ThreadDislikeId(),
    author: User,
    createdAt: ZonedDateTime = ZonedDateTime.now(),
    threadId: ThreadId
) : Entity<ThreadDislikeId>(id) {

    var author = author; private set
    var createdAt = createdAt; private set
    var threadId = threadId; private set
}


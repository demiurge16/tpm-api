package net.nuclearprometheus.tpm.applicationserver.domain.model.thread

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity

class Tag(
    id: TagId = TagId(),
    name: String,
    threadId: ThreadId
) : Entity<TagId>(id) {

    var name = name; private set
    var threadId = threadId; private set
}
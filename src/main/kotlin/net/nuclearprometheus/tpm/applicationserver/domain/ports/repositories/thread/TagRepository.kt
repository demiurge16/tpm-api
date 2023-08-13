package net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.thread

import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Tag
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.TagId
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.repositories.BaseRepository

interface TagRepository : BaseRepository<Tag, TagId> {

    fun getAllByThreadId(threadId: ThreadId): List<Tag>
    fun deleteAllByThreadId(threadId: ThreadId)
}
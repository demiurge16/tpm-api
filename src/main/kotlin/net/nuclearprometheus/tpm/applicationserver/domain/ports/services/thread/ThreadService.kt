package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.thread

import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.Thread
import net.nuclearprometheus.tpm.applicationserver.domain.model.thread.ThreadId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId

interface ThreadService {
    fun create(title: String, content: String, tags: List<String>, projectId: ProjectId): Thread
    fun update(id: ThreadId, title: String, content: String, tags: List<String>): Thread
    fun addLike(id: ThreadId): Thread
    fun removeLike(id: ThreadId): Thread
    fun addDislike(id: ThreadId): Thread
    fun removeDislike(id: ThreadId): Thread
    fun activate(id: ThreadId): Thread
    fun freeze(id: ThreadId): Thread
    fun close(id: ThreadId): Thread
    fun archive(id: ThreadId): Thread
    fun delete(id: ThreadId): Thread
}

package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.task

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import java.math.BigDecimal
import java.time.ZonedDateTime

class TaskServiceImpl : TaskService {
    override fun getAll(): List<Task> {
        TODO("Not yet implemented")
    }

    override fun get(id: TaskId): Task {
        TODO("Not yet implemented")
    }

    override fun create(
        title: String,
        description: String,
        sourceLanguage: LanguageCode,
        targetLanguage: LanguageCode,
        accuracy: AccuracyId,
        industry: IndustryId,
        unit: UnitId,
        amount: Int,
        expectedStart: ZonedDateTime,
        deadline: ZonedDateTime,
        budget: BigDecimal,
        currency: CurrencyCode,
        projectId: ProjectId
    ): Task {
        TODO("Not yet implemented")
    }

    override fun update(
        id: TaskId,
        title: String,
        description: String,
        sourceLanguage: LanguageCode,
        targetLanguage: LanguageCode,
        accuracy: AccuracyId,
        industry: IndustryId,
        unit: UnitId,
        amount: Int,
        budget: BigDecimal,
        currency: CurrencyCode
    ) {
        TODO("Not yet implemented")
    }

    override fun moveStart(id: TaskId, expectedStart: ZonedDateTime) {
        TODO("Not yet implemented")
    }

    override fun moveDeadline(id: TaskId, deadline: ZonedDateTime) {
        TODO("Not yet implemented")
    }

    override fun assignTeamMember(id: TaskId, teamMemberId: TeamMemberId) {
        TODO("Not yet implemented")
    }

    override fun unassignTeamMember(id: TaskId) {
        TODO("Not yet implemented")
    }

    override fun start(id: TaskId) {
        TODO("Not yet implemented")
    }

    override fun complete(id: TaskId) {
        TODO("Not yet implemented")
    }

    override fun requestRevisions(id: TaskId) {
        TODO("Not yet implemented")
    }

    override fun completeRevisions(id: TaskId) {
        TODO("Not yet implemented")
    }

    override fun deliver(id: TaskId) {
        TODO("Not yet implemented")
    }

    override fun cancel(id: TaskId) {
        TODO("Not yet implemented")
    }

    override fun reopen(id: TaskId) {
        TODO("Not yet implemented")
    }
}
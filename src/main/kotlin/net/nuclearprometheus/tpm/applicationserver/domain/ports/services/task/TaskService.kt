package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.task

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskId
import net.nuclearprometheus.tpm.applicationserver.domain.model.teammember.TeamMemberId
import java.math.BigDecimal
import java.time.ZonedDateTime

interface TaskService {

    fun create(
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
    ): Task

    fun update(
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
        currency: CurrencyCode,
    )

    fun moveStart(id: TaskId, expectedStart: ZonedDateTime)
    fun moveDeadline(id: TaskId, deadline: ZonedDateTime)
    fun assignTeamMember(id: TaskId, teamMemberId: TeamMemberId)
    fun unassignTeamMember(id: TaskId)
    fun start(id: TaskId)
    fun complete(id: TaskId)
    fun requestRevisions(id: TaskId)
    fun completeRevisions(id: TaskId)
    fun deliver(id: TaskId)
    fun cancel(id: TaskId)
    fun reopen(id: TaskId)
}

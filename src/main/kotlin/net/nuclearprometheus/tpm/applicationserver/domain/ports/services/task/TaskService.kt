package net.nuclearprometheus.tpm.applicationserver.domain.ports.services.task

import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.*
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.TaskId
import net.nuclearprometheus.tpm.applicationserver.domain.model.user.UserId
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
        serviceTypeId: ServiceTypeId,
        amount: Int,
        expectedStart: ZonedDateTime,
        deadline: ZonedDateTime,
        budget: BigDecimal,
        currency: CurrencyCode,
        priorityId: PriorityId,
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
        serviceTypeId: ServiceTypeId,
        amount: Int,
        budget: BigDecimal,
        currency: CurrencyCode,
    ): Task

    fun moveStart(id: TaskId, expectedStart: ZonedDateTime): Task
    fun moveDeadline(id: TaskId, deadline: ZonedDateTime): Task
    fun changePriority(id: TaskId, priorityId: PriorityId): Task
    fun assign(id: TaskId, assigneeId: UserId): Task
    fun unassign(id: TaskId): Task
    fun start(id: TaskId): Task
    fun startReview(id: TaskId): Task
    fun reject(id: TaskId): Task
    fun approve(id: TaskId): Task
    fun putOnHold(id: TaskId): Task
    fun resume(id: TaskId): Task
    fun complete(id: TaskId): Task
    fun cancel(id: TaskId): Task
    fun reopen(id: TaskId): Task
}


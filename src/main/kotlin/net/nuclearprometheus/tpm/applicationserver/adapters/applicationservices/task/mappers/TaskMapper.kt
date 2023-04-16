package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TaskResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task

object TaskMapper {
    fun Task.toView() = TaskResponse.View(
        id = id.value,
        title = title,
        description = description,
        sourceLanguage = TaskResponse.View.LanguageView(
            code = sourceLanguage.id.value,
            name = sourceLanguage.name
        ),
        targetLanguage = TaskResponse.View.LanguageView(
            code = targetLanguage.id.value,
            name = targetLanguage.name
        ),
        accuracy = TaskResponse.View.AccuracyView(
            id = accuracy.id.value,
            name = accuracy.name,
            description = accuracy.description
        ),
        industry = TaskResponse.View.IndustryView(
            id = industry.id.value,
            name = industry.name,
            description = industry.description
        ),
        unit = TaskResponse.View.UnitView(
            id = unit.id.value,
            name = unit.name,
            description = unit.description
        ),
        amount = amount,
        expectedStart = expectedStart,
        deadline = deadline,
        budget = budget,
        currency = TaskResponse.View.CurrencyView(
            code = currency.id.value,
            name = currency.name
        ),
        status = TaskResponse.View.TaskStatusView(
            status = status,
            name = status.name,
            description = status.description
        ),
        priority = TaskResponse.View.PriorityView(
            id = priority.id.value,
            name = priority.name,
            description = priority.description,
            emoji = priority.emoji,
            value = priority.value
        ),
        assignee = assignee?.let {
            TaskResponse.View.AssigneeView(
                teamMemberId = it.id.value,
                userId = it.user.id.value,
                firstName = it.user.firstName,
                lastName = it.user.lastName,
                email = it.user.email
            )
        },
        projectId = projectId.value
    )

    fun Task.toStartMoved() = TaskResponse.StartMoved(taskId = id.value, start = expectedStart)

    fun Task.toDeadlineMoved() = TaskResponse.DeadlineMoved(taskId = id.value, deadline = deadline)

}
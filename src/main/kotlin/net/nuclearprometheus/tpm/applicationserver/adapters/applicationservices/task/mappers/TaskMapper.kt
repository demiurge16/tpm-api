package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.*
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.Unit
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task

object TaskMapper {

    fun Task.toView(project: Project) = TaskResponse.Task(
        id = id.value,
        title = title,
        description = description,
        sourceLanguage = Language(
            code = sourceLanguage.id.value,
            name = sourceLanguage.name
        ),
        targetLanguage = Language(
            code = targetLanguage.id.value,
            name = targetLanguage.name
        ),
        accuracy = Accuracy(
            id = accuracy.id.value,
            name = accuracy.name,
            description = accuracy.description
        ),
        industry = Industry(
            id = industry.id.value,
            name = industry.name,
            description = industry.description
        ),
        unit = Unit(
            id = unit.id.value,
            name = unit.name,
            description = unit.description
        ),
        serviceType = ServiceType(
            id = serviceType.id.value,
            name = serviceType.name,
            description = serviceType.description
        ),
        amount = amount,
        expectedStart = expectedStart,
        deadline = deadline,
        budget = budget,
        currency = Currency(
            code = currency.id.value,
            name = currency.name
        ),
        status = Status(
            status = status,
            title = status.title,
            description = status.description
        ),
        priority = Priority(
            id = priority.id.value,
            name = priority.name,
            description = priority.description,
            emoji = priority.emoji,
            value = priority.value
        ),
        assignee = assignee?.let {
            Assignee(
                userId = it.id.value,
                firstName = it.firstName,
                lastName = it.lastName,
                email = it.email
            )
        },
        project = ProjectShortView(
            id = project.id.value,
            title = project.title,
            status = ProjectStatus(
                status = project.status,
                title = project.status.title,
                description = project.status.description
            ),
        )
    )

    fun Task.toStartMoved() = TaskResponse.StartMoved(taskId = id.value, start = expectedStart)

    fun Task.toDeadlineMoved() = TaskResponse.DeadlineMoved(taskId = id.value, deadline = deadline)

}
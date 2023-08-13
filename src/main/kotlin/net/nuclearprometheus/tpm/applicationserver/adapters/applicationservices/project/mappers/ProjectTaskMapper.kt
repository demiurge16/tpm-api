package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.*
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.Unit
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task

object ProjectTaskMapper {

    fun Task.toView() = TaskResponse.Task(
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
        amount = amount,
        expectedStart = expectedStart,
        deadline = deadline,
        budget = budget,
        currency = Currency(
            code = currency.id.value,
            name = currency.name
        ),
        priority = Priority(
            id = priority.id.value,
            name = priority.name,
            description = priority.description,
            emoji = priority.emoji,
            value = priority.value
        ),
        status = Status(
            status = status,
            name = status.title,
            description = status.description,
        ),
        assignee = assignee?.let {
            Assignee(
                userId = it.id.value,
                firstName = it.firstName,
                lastName = it.lastName,
                email = it.email
            )
        },
        projectId = projectId.value
    )
}
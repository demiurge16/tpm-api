package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectTaskResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task

object ProjectTaskMapper {

    fun Task.toView() = ProjectTaskResponse.View(
        id = id.value,
        title = title,
        description = description,
        sourceLanguage = ProjectTaskResponse.View.LanguageView(
            code = sourceLanguage.id.value,
            name = sourceLanguage.name
        ),
        targetLanguage = ProjectTaskResponse.View.LanguageView(
            code = targetLanguage.id.value,
            name = targetLanguage.name
        ),
        accuracy = ProjectTaskResponse.View.AccuracyView(
            id = accuracy.id.value,
            name = accuracy.name,
            description = accuracy.description
        ),
        industry = ProjectTaskResponse.View.IndustryView(
            id = industry.id.value,
            name = industry.name,
            description = industry.description
        ),
        unit = ProjectTaskResponse.View.UnitView(
            id = unit.id.value,
            name = unit.name,
            description = unit.description
        ),
        amount = amount,
        expectedStart = expectedStart,
        deadline = deadline,
        budget = budget,
        currency = ProjectTaskResponse.View.CurrencyView(
            code = currency.id.value,
            name = currency.name
        ),
        priority = ProjectTaskResponse.View.PriorityView(
            id = priority.id.value,
            name = priority.name,
            description = priority.description,
            emoji = priority.emoji,
            value = priority.value
        ),
        projectId = projectId.value
    )
}
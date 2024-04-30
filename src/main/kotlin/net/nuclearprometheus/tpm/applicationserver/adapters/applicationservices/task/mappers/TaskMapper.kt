package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.mappers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.AccuracyMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.CurrencyMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.IndustryMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.LanguageMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.PriorityMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.ServiceTypeMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.dictionaries.mappers.UnitMapper.toView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers.ProjectMapper.toShortView
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TaskDeadlineMoved
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TaskStartMoved
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.TaskStatus
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.user.mappers.UserMapper.toView
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.Project
import net.nuclearprometheus.tpm.applicationserver.domain.model.task.Task
import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.task.responses.Task as TaskResponse

object TaskMapper {

    fun Task.toView(project: Project) = TaskResponse(
        id = id.value,
        title = title,
        description = description,
        sourceLanguage = sourceLanguage.toView(),
        targetLanguage = targetLanguage.toView(),
        accuracy = accuracy.toView(),
        industry = industry.toView(),
        unit = unit.toView(),
        serviceType = serviceType.toView(),
        amount = amount,
        expectedStart = expectedStart,
        deadline = deadline,
        budget = budget,
        currency = currency.toView(),
        status = TaskStatus(
            status = status,
            title = status.title,
            description = status.description
        ),
        priority = priority.toView(),
        assignee = assignee?.toView(),
        project = project.toShortView()
    )

    fun Task.toStartMoved() = TaskStartMoved(taskId = id.value, start = expectedStart)

    fun Task.toDeadlineMoved() = TaskDeadlineMoved(taskId = id.value, deadline = deadline)

}

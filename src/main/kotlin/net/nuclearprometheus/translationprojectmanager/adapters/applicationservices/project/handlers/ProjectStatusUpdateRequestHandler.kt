package net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.handlers

import net.nuclearprometheus.translationprojectmanager.adapters.applicationservices.project.responses.ProjectStatusUpdateResponse
import java.util.*

interface ProjectStatusUpdateRequestHandler {
    fun finishDraft(id: UUID): ProjectStatusUpdateResponse
    fun backToDraft(id: UUID): ProjectStatusUpdateResponse
    fun startProgress(id: UUID): ProjectStatusUpdateResponse
    fun finishProgress(id: UUID): ProjectStatusUpdateResponse
    fun backToProgress(id: UUID): ProjectStatusUpdateResponse
    fun deliver(id: UUID): ProjectStatusUpdateResponse
    fun invoice(id: UUID): ProjectStatusUpdateResponse
    fun pay(id: UUID): ProjectStatusUpdateResponse
}

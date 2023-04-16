package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.mappers.ProjectStatusMapper.toProjectStatusResponse
import net.nuclearprometheus.tpm.applicationserver.domain.model.project.ProjectId
import net.nuclearprometheus.tpm.applicationserver.domain.ports.services.project.ProjectService
import net.nuclearprometheus.tpm.applicationserver.logging.loggerFor
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProjectStatusApplicationService(
    private val service: ProjectService
) {

    private val logger = loggerFor(ProjectStatusApplicationService::class.java)

    fun finishDraft(id: UUID) = with(logger) {
        info("finishDraft($id)")

        service.finishDraft(ProjectId(id)).toProjectStatusResponse()
    }

    fun backToDraft(id: UUID) = with(logger) {
        info("backToDraft($id)")

        service.backToDraft(ProjectId(id)).toProjectStatusResponse()
    }

    fun startProgress(id: UUID) = with(logger) {
        info("startProgress($id)")

        service.startProgress(ProjectId(id)).toProjectStatusResponse()
    }

    fun finishProgress(id: UUID) = with(logger) {
        info("finishProgress($id)")

        service.finishProgress(ProjectId(id)).toProjectStatusResponse()
    }

    fun backToProgress(id: UUID) = with(logger) {
        info("backToProgress($id)")

        service.backToProgress(ProjectId(id)).toProjectStatusResponse()
    }

    fun deliver(id: UUID) = with(logger) {
        info("deliver($id)")

        service.deliver(ProjectId(id)).toProjectStatusResponse()
    }

    fun invoice(id: UUID) = with(logger) {
        info("invoice($id)")

        service.invoice(ProjectId(id)).toProjectStatusResponse()
    }

    fun pay(id: UUID) = with(logger) {
        info("pay($id)")

        service.pay(ProjectId(id)).toProjectStatusResponse()
    }

    fun putOnHold(id: UUID) = with(logger) {
        info("putOnHold($id)")

        service.putOnHold(ProjectId(id)).toProjectStatusResponse()
    }

    fun resume(id: UUID) = with(logger) {
        info("resume($id)")

        service.resume(ProjectId(id)).toProjectStatusResponse()
    }

    fun cancel(id: UUID) = with(logger) {
        info("cancel($id)")

        service.cancel(ProjectId(id)).toProjectStatusResponse()
    }

    fun reopen(id: UUID) = with(logger) {
        info("reopen($id)")

        service.reopen(ProjectId(id)).toProjectStatusResponse()
    }
}
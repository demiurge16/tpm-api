package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectStatusUpdateResponse
import org.springframework.stereotype.Service
import java.util.*

@Service class ProjectStatusUpdateRequestHandlerImpl : ProjectStatusUpdateRequestHandler {

    override fun finishDraft(id: UUID): ProjectStatusUpdateResponse {
        TODO("Not yet implemented")
    }

    override fun backToDraft(id: UUID): ProjectStatusUpdateResponse {
        TODO("Not yet implemented")
    }

    override fun startProgress(id: UUID): ProjectStatusUpdateResponse {
        TODO("Not yet implemented")
    }

    override fun finishProgress(id: UUID): ProjectStatusUpdateResponse {
        TODO("Not yet implemented")
    }

    override fun backToProgress(id: UUID): ProjectStatusUpdateResponse {
        TODO("Not yet implemented")
    }

    override fun deliver(id: UUID): ProjectStatusUpdateResponse {
        TODO("Not yet implemented")
    }

    override fun invoice(id: UUID): ProjectStatusUpdateResponse {
        TODO("Not yet implemented")
    }

    override fun pay(id: UUID): ProjectStatusUpdateResponse {
        TODO("Not yet implemented")
    }
}
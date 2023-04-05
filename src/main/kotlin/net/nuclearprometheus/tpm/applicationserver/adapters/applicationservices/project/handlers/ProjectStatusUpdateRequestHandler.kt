package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.handlers

import net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.responses.ProjectStatusUpdateResponse
import org.springframework.stereotype.Service
import java.util.*

@Service class ProjectStatusUpdateRequestHandler {

    fun finishDraft(id: UUID): ProjectStatusUpdateResponse {
        TODO("Not yet implemented")
    }

    fun backToDraft(id: UUID): ProjectStatusUpdateResponse {
        TODO("Not yet implemented")
    }

    fun startProgress(id: UUID): ProjectStatusUpdateResponse {
        TODO("Not yet implemented")
    }

    fun finishProgress(id: UUID): ProjectStatusUpdateResponse {
        TODO("Not yet implemented")
    }

    fun backToProgress(id: UUID): ProjectStatusUpdateResponse {
        TODO("Not yet implemented")
    }

    fun deliver(id: UUID): ProjectStatusUpdateResponse {
        TODO("Not yet implemented")
    }

    fun invoice(id: UUID): ProjectStatusUpdateResponse {
        TODO("Not yet implemented")
    }

    fun pay(id: UUID): ProjectStatusUpdateResponse {
        TODO("Not yet implemented")
    }
}
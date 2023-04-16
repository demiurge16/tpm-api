package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests

sealed class ProjectNoteRequest {
    data class Create(val content: String) : ProjectNoteRequest()
}
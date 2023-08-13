package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests

sealed class ProjectThreadRequest {
    data class Create(val title: String, val content: String, val tags: List<String>) : ProjectThreadRequest()
}
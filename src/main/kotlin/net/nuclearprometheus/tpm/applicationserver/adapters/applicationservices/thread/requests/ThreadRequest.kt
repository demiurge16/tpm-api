package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.requests

sealed class ThreadRequest {
    data class Update(
        val title: String,
        val content: String,
        val tags: List<String>
    ) : ThreadRequest()
}
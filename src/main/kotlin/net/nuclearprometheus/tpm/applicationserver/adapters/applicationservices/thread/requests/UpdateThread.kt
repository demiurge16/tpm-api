package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.thread.requests

data class UpdateThread(
    val title: String,
    val content: String,
    val tags: List<String>
)
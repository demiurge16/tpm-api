package net.nuclearprometheus.tpm.applicationserver.adapters.applicationservices.project.requests

data class CreateThread(val title: String, val content: String, val tags: List<String>)

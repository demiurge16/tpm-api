package net.nuclearprometheus.tpm.applicationserver.logging

enum class ThreadContextKey(val key: String) {
    CORRELATION_ID("correlationId"),
    SCOPE_ID("scopeId")
}

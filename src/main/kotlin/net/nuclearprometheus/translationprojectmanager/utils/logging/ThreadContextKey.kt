package net.nuclearprometheus.translationprojectmanager.utils.logging

enum class ThreadContextKey(val key: String) {
    CORRELATION_ID("correlationId"),
    SCOPE_ID("scopeId")
}

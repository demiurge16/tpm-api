package net.nuclearprometheus.tpm.applicationserver.logging

import kotlin.concurrent.thread

fun parentContextAwareThread(
    correlationId: String? = LoggingContext.correlationId(),
    scopeId: String = LoggingContext.newScopeId(),
    name: String? = null,
    isDaemon: Boolean = false,
    contextClassLoader: ClassLoader? = null,
    start: Boolean = true,
    priority: Int = -1,
    block: () -> Unit
): Thread = thread(
    name = name,
    isDaemon = isDaemon,
    contextClassLoader = contextClassLoader,
    start = start,
    priority = priority
) {
    LoggingContext.newCorrelationId(correlationId ?: "")
    LoggingContext.newScopeId(scopeId)
    block()
}

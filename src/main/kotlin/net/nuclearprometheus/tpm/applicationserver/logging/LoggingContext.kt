package net.nuclearprometheus.tpm.applicationserver.logging

import org.apache.logging.log4j.ThreadContext
import java.util.*

object LoggingContext {
    fun correlationId(): String = ThreadContext.get(ThreadContextKey.CORRELATION_ID.key)
    fun newCorrelationId(): String = UUID.randomUUID().toString()
        .also { ThreadContext.put(ThreadContextKey.CORRELATION_ID.key, it) }

    fun newCorrelationId(correlationId: String) =
        ThreadContext.put(ThreadContextKey.CORRELATION_ID.key, correlationId)

    fun scopeId(): String = ThreadContext.get(ThreadContextKey.SCOPE_ID.key)
    fun newScopeId(): String = UUID.randomUUID().toString()
        .also { ThreadContext.put(ThreadContextKey.SCOPE_ID.key, it) }

    fun newScopeId(scopeId: String) =
        ThreadContext.put(ThreadContextKey.SCOPE_ID.key, scopeId)
}

package net.nuclearprometheus.tpm.applicationserver.config.logging

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import net.nuclearprometheus.tpm.applicationserver.logging.LoggingContext
import net.nuclearprometheus.tpm.applicationserver.logging.ThreadContextKey
import org.apache.logging.log4j.ThreadContext
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class CorrelationIdInterceptor : HandlerInterceptor {

    private val correlationIdHeader = "X-Correlation-ID"
    private val correlationIdKey = ThreadContextKey.CORRELATION_ID.key

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val correlationId = request.getHeader(correlationIdHeader) ?: LoggingContext.newCorrelationId()
        ThreadContext.put(correlationIdKey, correlationId)
        response.addHeader(correlationIdHeader, correlationId)
        return true
    }

    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex: Exception?) {
        ThreadContext.remove(correlationIdKey)
    }
}

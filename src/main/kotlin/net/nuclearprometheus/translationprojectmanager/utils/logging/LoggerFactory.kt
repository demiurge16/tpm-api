package net.nuclearprometheus.translationprojectmanager.utils.logging

import net.nuclearprometheus.translationprojectmanager.domain.ports.services.logging.Logger
import org.slf4j.LoggerFactory

fun loggerFor(clazz: Class<*>): Logger = LoggerImpl(LoggerFactory.getLogger(clazz))

fun loggerFor(name: String): Logger = LoggerImpl(LoggerFactory.getLogger(name))

private class LoggerImpl : Logger {

    private val logger: org.slf4j.Logger

    constructor(logger: org.slf4j.Logger) {
        this.logger = logger
    }

    constructor(clazz: Class<*>) {
        this.logger = LoggerFactory.getLogger(clazz)
    }

    constructor(name: String) {
        this.logger = LoggerFactory.getLogger(name)
    }

    override fun trace(message: String) = logger.trace(message)
    override fun debug(message: String) = logger.debug(message)
    override fun info(message: String) = logger.info(message)
    override fun warn(message: String) = logger.warn(message)
    override fun error(message: String)  = logger.error(message)
    override fun trace(message: String, throwable: Throwable) = logger.trace(message, throwable)
    override fun debug(message: String, throwable: Throwable) = logger.debug(message, throwable)
    override fun info(message: String, throwable: Throwable) = logger.info(message, throwable)
    override fun warn(message: String, throwable: Throwable) = logger.warn(message, throwable)
    override fun error(message: String, throwable: Throwable) = logger.error(message, throwable)

}
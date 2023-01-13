package net.nuclearprometheus.translationprojectmanager.domain.ports.services.logging

interface Logger {
    fun trace(message: String)
    fun debug(message: String)
    fun info(message: String)
    fun warn(message: String)
    fun error(message: String)

    fun trace(message: String, throwable: Throwable)
    fun debug(message: String, throwable: Throwable)
    fun info(message: String, throwable: Throwable)
    fun warn(message: String, throwable: Throwable)
    fun error(message: String, throwable: Throwable)
}
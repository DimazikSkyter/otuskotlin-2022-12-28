package ru.otus.escalop.plugins

import ch.qos.logback.classic.Logger
import io.ktor.server.application.*
import ru.otus.common.logging.CustomLoggerProvider
import ru.otus.common.logging.ILogWrapper

fun Application.getLoggerProviderConf(): CustomLoggerProvider =
    when (val mode = environment.config.propertyOrNull("ktor.logger")?.getString()) {
        "logback" -> CustomLoggerProvider()
        else -> throw Exception("Logger $mode is not allowed. Additted values are kmp and logback")
    }
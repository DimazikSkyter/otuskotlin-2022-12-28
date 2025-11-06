package ru.escalop.escalop.plugins

import io.ktor.server.application.*
import ru.escalop.common.logging.CustomLoggerProvider

fun Application.getLoggerProviderConf(): CustomLoggerProvider =
    when (val mode = environment.config.propertyOrNull("ktor.logger")?.getString()) {
        "logback" -> CustomLoggerProvider()
        else -> throw Exception("Logger $mode is not allowed. Additted values are kmp and logback")
    }
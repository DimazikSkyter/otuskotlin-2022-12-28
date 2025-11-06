package ru.escalop.escalop

import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import ru.escalop.escalop.plugins.initAppSettings
import ru.escalop.escalop.plugins.initPlugins
import ru.escalop.escalop.settings.EscalopAppSettings

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module(appSettings: EscalopAppSettings = initAppSettings()) {
    initPlugins(appSettings)
    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
        webSocket("/ws/v1") {
            wsHandlerV1(appSettings)
        }
    }
}

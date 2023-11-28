package ru.otus.escalop

import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import ru.otus.cassandra.CassandraClientStub
import ru.otus.escalop.plugins.initAppSettings
import ru.otus.escalop.plugins.initPlugins
import ru.otus.escalop.settings.EscalopAppSettings
import ru.otus.google.GoogleCalendarStub
import ru.otus.mapper.apiV1Mapper

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

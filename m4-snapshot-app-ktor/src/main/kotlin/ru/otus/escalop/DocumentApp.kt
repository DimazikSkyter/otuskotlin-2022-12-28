package ru.otus.escalop

import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.otus.cassandra.CassandraClientStub
import ru.otus.google.GoogleCalendarStub
import ru.otus.mapper.apiV1Mapper


fun Application.run(processor: DocumentProcessor) {
    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
        route("v1") {
            install(ContentNegotiation) {
                register(ContentType.Application.Json, JacksonConverter(apiV1Mapper))
            }
            documentOperationsCall(processor)
        }
    }
}


fun main() {
    val cassandraClient = CassandraClientStub()
    val googleClient = GoogleCalendarStub()
    embeddedServer(CIO, port = 8080) {
        run(DocumentProcessor(googleClient, cassandraClient))
    }.start(wait = true)
}

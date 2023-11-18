package ru.otus.escalop

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.otus.cassandra.CassandraClientStub
import ru.otus.google.GoogleCalendarStub

fun Route.documentOperationsCall(processor: DocumentProcessor) {
    route("document") {
        post("read") {
            call.readDocument(processor)
        }
        post("search") {
            call.searchDocument(processor)
        }
        post("upload") {
            call.uploadDocument(processor)
        }
    }
}
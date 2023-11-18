package ru.otus.escalop

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.otus.api.v1.models.DocumentUploadRequest
import ru.otus.api.v1.models.SnapshotReadRequest
import ru.otus.api.v1.models.SnapshotSearchRequest
import ru.otus.common.EsculapContext
import ru.otus.mappers.fromTransport
import ru.otus.mappers.toTransportRead
import ru.otus.mappers.toTransportSearch
import ru.otus.mappers.toTransportUpload

suspend fun ApplicationCall.uploadDocument(processor: DocumentProcessor) {
    val request = receive<DocumentUploadRequest>()
    val context = EsculapContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportUpload())
}

suspend fun ApplicationCall.readDocument(processor: DocumentProcessor) {
    val request = receive<SnapshotReadRequest>()
    val context = EsculapContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportRead())
}

suspend fun ApplicationCall.searchDocument(processor: DocumentProcessor) {
    val request = receive<SnapshotSearchRequest>()
    val context = EsculapContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportSearch())
}
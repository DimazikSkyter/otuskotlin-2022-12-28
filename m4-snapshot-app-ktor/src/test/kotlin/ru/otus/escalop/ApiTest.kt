package ru.otus.escalop

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import ru.otus.api.v1.models.*
import ru.otus.cassandra.CassandraClientStub
import ru.otus.google.GoogleCalendarStub
import ru.otus.mapper.apiV1RequestSerialize
import ru.otus.mapper.apiV1ResponseDeserialize
import java.math.BigDecimal
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.test.Test
import kotlin.test.assertEquals

class ApiTest {

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun upload() = testApplication {
        application { run(DocumentProcessor(GoogleCalendarStub(), CassandraClientStub())) }
        val expected = "1230"
        val userId = "test-user-1"
        val response = client.post("/v1/document/upload") {
            val requestObj = DocumentUploadRequest(
                "test-user-1",
                "upload",
                expected,
                SnapshotDebug(SnapshotRequestDebugMode.STUB, SnapshotRequestDebugStubs.SUCCESS),
                DocumentUploadObject(
                    "test_document_name", DocumentType.GENERAL, Base64.encode("pdf_base64".toByteArray())
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiV1RequestSerialize(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV1ResponseDeserialize<DocumentUploadResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals(userId, responseObj.userId)
        assertEquals(expected, responseObj.requestId)
        assertEquals("Document successful uploaded", responseObj.message)
        println(responseObj)
    }

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun read() = testApplication {
        application { run(DocumentProcessor(GoogleCalendarStub(), CassandraClientStub())) }
        val requestId = "1231"
        val userId = "test-user-2"
        val response = client.post("/v1/document/read") {
            val requestObj = SnapshotReadRequest(
                userId,
                "read",
                requestId,
                SnapshotDebug(SnapshotRequestDebugMode.STUB, SnapshotRequestDebugStubs.SUCCESS),
                SnapshotReadObject(BigDecimal(1))
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiV1RequestSerialize(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV1ResponseDeserialize<SnapshotReadResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals(
            "{\"id\":1,\"type\":\"BLOOD_GENERAL\",\"date\":\"2023-05-05\",\"metrics\":{\"m1\":33.3,\"m2\":32.5},\"name\":\"test snapshot\"}",
            responseObj.snapshot
        )
        assertEquals(requestId, responseObj.requestId)
        assertEquals(userId, responseObj.userId)
        println(responseObj)
    }


    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun search() = testApplication {
        application { run(DocumentProcessor(GoogleCalendarStub(), CassandraClientStub())) }
        val requestId = "1232"
        val userId = "test-user-3"
        val response = client.post("/v1/document/search") {
            val requestObj = SnapshotSearchRequest(
                userId,
                "search",
                requestId,
                SnapshotDebug(SnapshotRequestDebugMode.STUB, SnapshotRequestDebugStubs.SUCCESS),
                SnapshotSearchFilter("namePart=test")
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiV1RequestSerialize(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV1ResponseDeserialize<SnapshotSearchResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals(2, responseObj.list!!.size)
        assertEquals("test 1", responseObj.list!![0].name)
        assertEquals("test 2", responseObj.list!![1].name)
        println(responseObj)
    }
}
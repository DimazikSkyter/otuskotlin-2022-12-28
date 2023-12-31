package ru.otus.escalop

import io.ktor.client.plugins.websocket.*
import io.ktor.server.testing.*
import io.ktor.websocket.*
import kotlinx.coroutines.withTimeout
import kotlinx.datetime.LocalDate
import ru.otus.api.v1.models.*
import ru.otus.cassandra.CassandraClientStub
import ru.otus.common.EscalopCorSettings
import ru.otus.common.calendar.CalendarSnapshot
import ru.otus.common.entity.Metric
import ru.otus.common.entity.Snapshot
import ru.otus.common.entity.ValueInfo
import ru.otus.common.model.SnapshotId
import ru.otus.common.model.WorkMode
import ru.otus.escalop.biz.SnapshotProcessor
import ru.otus.escalop.settings.EscalopAppSettings
import ru.otus.google.GoogleCalendarStub
import ru.otus.mapper.apiV1Mapper
import ru.otus.mapper.apiV1ResponseDeserialize
import java.math.BigDecimal
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.test.Test
import kotlin.test.assertEquals

class ApiTest {
    private val escalopCorSettings: EscalopCorSettings =
        EscalopCorSettings(
            repoStub = CassandraClientStub(
                mutableMapOf(
                    Pair(
                        "test-user-3", mutableMapOf(
                            Pair(
                                SnapshotId(3), Snapshot(
                                    3,
                                    ru.otus.common.model.DocumentType.BLOOD_GENERAL,
                                    LocalDate(2023, 1, 2),
                                    listOf("abc", "abv"),
                                    "test 1"
                                )
                            ), Pair(
                                SnapshotId(4), Snapshot(
                                    4,
                                    ru.otus.common.model.DocumentType.BLOOD_GENERAL,
                                    LocalDate(2023, 3, 4),
                                    listOf("abc", "abv"),
                                    "test 2"
                                )
                            )
                        )
                    )
                )
            ),
            calendarStub = GoogleCalendarStub(
                mutableMapOf(
                    Pair(
                        1L,
                        CalendarSnapshot(
                            "3",
                            LocalDate(2023, 5, 5),
                            ru.otus.common.model.DocumentType.BLOOD_GENERAL,
                            "test snapshot",
                            listOf(Metric("m1", ValueInfo(33.3, "33-34")), Metric("m2", ValueInfo(32.5, "30-34")))
                        )
                    )
                )
            )
        )
    private val snapshotProcessor: SnapshotProcessor = SnapshotProcessor(escalopCorSettings)
    private val escalopAppSettings: EscalopAppSettings =
        EscalopAppSettings(listOf("ws://127.0.0.1:8080/v1"), snapshotProcessor, escalopCorSettings)


    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun upload() = testApplication {
        val expected = "1230"
        val userId = "test-user-1"
        val requestObj = DocumentUploadRequest(
            "test-user-1",
            "upload",
            expected,
            SnapshotDebug(SnapshotRequestDebugMode.STUB, SnapshotRequestDebugStubs.SUCCESS),
            DocumentUploadObject(
                "test_document_name", DocumentType.GENERAL, Base64.encode("pdf_base64".toByteArray())
            )
        )

        testMethod<IResponse>(requestObj) {
            if (it is DocumentUploadResponse) {
                assertEquals(userId, it.userId)
                assertEquals(expected, it.requestId)
                assertEquals("Document successful uploaded", it.message)
            } else {
                throw AssertionError("Wrong type of IResponse")
            }
        }
    }

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun read() = testApplication {
        val requestId = "1231"
        val userId = "test-user-2"
        val requestObj = SnapshotReadRequest(
            userId,
            "read",
            requestId,
            SnapshotDebug(SnapshotRequestDebugMode.STUB, SnapshotRequestDebugStubs.SUCCESS),
            SnapshotReadObject(BigDecimal(1))
        )

        testMethod<IResponse>(requestObj) {
            if (it is SnapshotReadResponse) {
                assertEquals(
                    "{\"id\":1,\"type\":\"BLOOD_GENERAL\",\"date\":\"2023-05-05\",\"metrics\":[\"m1\",\"m2\"],\"name\":\"test snapshot\"}",
                    it.snapshot
                )
                assertEquals(requestId, it.requestId)
                assertEquals(userId, it.userId)
            } else {
                throw AssertionError("Wrong type of IResponse")
            }
        }
    }


    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun search() = testApplication {
        application { module(escalopAppSettings) }
        val requestId = "1232"
        val userId = "test-user-3"
        val requestObj = SnapshotSearchRequest(
            userId,
            "search",
            requestId,
            SnapshotDebug(SnapshotRequestDebugMode.STUB, SnapshotRequestDebugStubs.SUCCESS),
            SnapshotSearchFilter("namePart=test")
        )
        testMethod<IResponse>(requestObj) {
            if (it is SnapshotSearchResponse) {
                assertEquals(2, it.list!!.size)
                assertEquals("test 1", it.list!![0].name)
                assertEquals("test 2", it.list!![1].name)
            } else {
                throw AssertionError("Wrong type of IResponse")
            }
        }
    }


    private inline fun <reified T> testMethod(
        request: Any,
        crossinline assertBlock: (T) -> Unit
    ) = testApplication {
        application { module(escalopAppSettings) }
        val client = createClient {
            install(WebSockets)
        }

        client.webSocket("/ws/v1") {
            send(Frame.Text(apiV1Mapper.writeValueAsString(request)))
            withTimeout(3000) {
                val income = incoming.receive() as Frame.Text
                val response = apiV1ResponseDeserialize(income.readText(), T::class.java)

                assertBlock(response)
            }
        }
    }
}
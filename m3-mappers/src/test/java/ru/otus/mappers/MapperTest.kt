package ru.otus.mappers

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.junit.Test
import ru.otus.api.v1.models.*
import ru.otus.api.v1.models.SnapshotReadResponse
import ru.otus.api.v1.models.SnapshotSearchResponse
import ru.otus.common.EsculapContext
import ru.otus.common.NONE
import ru.otus.common.model.*
import ru.otus.common.model.DocumentType
import java.math.BigDecimal
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MapperTest {

    @Test
    fun uploadFromTransport() {
        val userId = "user_31"
        val requestId = "132145"
        val documentName = "doc name"
        val file = "file64str"
        val req = DocumentUploadRequest(
            userId = userId,
            requestId = requestId,
            debug = SnapshotDebug(SnapshotRequestDebugMode.PROD, SnapshotRequestDebugStubs.SUCCESS),
            uploadMetaData = DocumentUploadObject(documentName, ru.otus.api.v1.models.DocumentType.CHEMISTRY, file)
        )

        val context = EsculapContext()
        context.fromTransport(req)

        assertEquals(UserCommand.UPLOAD, context.command)
        assertEquals(userId, context.userId.asString())
        assertEquals(requestId, context.requestId.asString())
        assertTrue { context.userRequest is UploadDocumentRequest }
        assertEquals(documentName, (context.userRequest as UploadDocumentRequest).documentName)
        assertEquals(DocumentType.BLOOD_CHEMISTRY, (context.userRequest as UploadDocumentRequest).documentType)
        assertEquals(file, (context.userRequest as UploadDocumentRequest).fileBase64)
    }

    @Test
    fun readFromTransport() {
        val userId = "user_34"
        val requestId = "661321"
        val snapshotId = BigDecimal.valueOf(998)
        val req = SnapshotReadRequest(
            userId = userId,
            requestId = requestId,
            debug = SnapshotDebug(SnapshotRequestDebugMode.PROD, SnapshotRequestDebugStubs.SUCCESS),
            snapshot = SnapshotReadObject(
                snapshotId
            )
        )

        val context = EsculapContext()
        context.fromTransport(req)

        assertEquals(UserCommand.READ, context.command)
        assertEquals(userId, context.userId.asString())
        assertEquals(requestId, context.requestId.asString())
        assertTrue { context.userRequest is ReadSnapshotRequest }
        assertEquals(snapshotId.toLong(), (context.userRequest as ReadSnapshotRequest).id.get())
    }

    @Test
    fun searchFromTransport() {
        val userId = "user_34"
        val requestId = "661321"
        val date = "2012-12-03T14:31:33Z"
        val namePart = "фывп2пац"
        val req = SnapshotSearchRequest(
            userId = userId,
            requestId = requestId,
            debug = SnapshotDebug(SnapshotRequestDebugMode.PROD, SnapshotRequestDebugStubs.SUCCESS),
            snapshot = SnapshotSearchFilter("date=$date&namePart=$namePart")
        )

        val context = EsculapContext()
        context.fromTransport(req)

        assertEquals(UserCommand.SEARCH, context.command)
        assertEquals(userId, context.userId.asString())
        assertEquals(requestId, context.requestId.asString())
        assertTrue { context.userRequest is SearchSnapshotRequest }
        assertEquals(Instant.parse(date), (context.userRequest as SearchSnapshotRequest).userFilterRequest.date)
        assertEquals(namePart, (context.userRequest as SearchSnapshotRequest).userFilterRequest.namePart)
    }

    @Test
    fun uploadToTransport() {
        val requestId: RequestId = RequestId(6665)
        val userId: UserId = UserId("user_501")
        val documentName = "doc name 123"
        val context = EsculapContext (
            command = UserCommand.UPLOAD,
            state = RequestState.FINISH,
            errors = mutableListOf(),
            workMode = WorkMode.PROD,
            stubCase = Stubs.NONE,
            requestId = requestId,
            timeStart = Clock.System.now(),
            userId = userId,
            userRequest = UploadDocumentRequest (
                documentName,
                DocumentType.BLOOD_CHEMISTRY,
                "filedata 123"
            )
        )

        val req = context.toTransport() as DocumentUploadResponse

        assertEquals(requestId.asString(), req.requestId)
        assertEquals(userId.asString(), req.userId)
        assertEquals(true, req.status)
        assertEquals(0, req.errors!!.size)
        assertEquals(ResponseResult.SUCCESS, req.result)
        assertEquals("File successful upload", req.message)
    }

    @Test
    fun readToTransport() {
        val requestId: RequestId = RequestId(1465)
        val userId: UserId = UserId("user_5221")
        val snapshotId: SnapshotId = SnapshotId(3125)
        val snapshotData = "some snapshot data json"
        val context = EsculapContext (
            command = UserCommand.READ,
            state = RequestState.FINISH,
            errors = mutableListOf(),
            workMode = WorkMode.PROD,
            stubCase = Stubs.NONE,
            requestId = requestId,
            timeStart = Clock.System.now(),
            userId = userId,
            userRequest = ReadSnapshotRequest (snapshotId),
            response = ru.otus.common.model.SnapshotReadResponse(
                snapshotId,
                snapshotData
            )
        )

        val req = context.toTransport() as SnapshotReadResponse

        assertEquals(requestId.asString(), req.requestId)
        assertEquals(userId.asString(), req.userId)
        assertEquals(0, req.errors!!.size)
        assertEquals(ResponseResult.SUCCESS, req.result)
        assertEquals(snapshotData, req.snapshot)
        assertEquals(snapshotId.get(), req.metadata!!.id!!.toLong())
    }

    @Test
    fun searchToTransport() {
        val requestId: RequestId = RequestId(6665)
        val userId: UserId = UserId("user_501")
        val snapshotId1 = SnapshotId(333)
        val snapshotId2 = SnapshotId(334)
        val date = "2012-12-03T00:00:00Z"
        val docName1 = "doc 1"
        val docName2 = "doc 2"
        val context = EsculapContext (
            command = UserCommand.SEARCH,
            state = RequestState.FINISH,
            errors = mutableListOf(),
            workMode = WorkMode.PROD,
            stubCase = Stubs.NONE,
            requestId = requestId,
            timeStart = Clock.System.now(),
            userId = userId,
            userRequest = SearchSnapshotRequest(
                UserFilterRequest(null, null, DocumentType.BLOOD_GENERAL)
            ),
            response = ru.otus.common.model.SnapshotSearchResponse(
                listOf(SnapshotInfo(snapshotId1, Instant.parse(date), docName1), SnapshotInfo(snapshotId2, Instant.parse(date), docName2))
            )
        )

        val req = context.toTransport() as SnapshotSearchResponse

        assertEquals(requestId.asString(), req.requestId)
        assertEquals(userId.asString(), req.userId)
        assertEquals(0, req.errors!!.size)
        assertEquals(ResponseResult.SUCCESS, req.result)
        assertEquals(2, req.list!!.size)
        assertContains(req.list!!, SnapshotListObject(BigDecimal(333),  date, docName1))
        assertContains(req.list!!, SnapshotListObject(BigDecimal(334),  date, docName2))
    }
}
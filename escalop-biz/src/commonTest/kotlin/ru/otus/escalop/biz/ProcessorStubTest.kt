package ru.otus.escalop.biz

import kotlinx.coroutines.test.runTest
import ru.otus.cassandra.CassandraClientStub
import ru.otus.common.EscalopContext
import ru.otus.common.EscalopCorSettings
import ru.otus.common.model.*
import ru.otus.google.GoogleCalendarStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ProcessorStubTest {

    private val corSettings: EscalopCorSettings = EscalopCorSettings(
        repoStub = CassandraClientStub(),
        calendarStub = GoogleCalendarStub()
    )
    private val processor: SnapshotProcessor = SnapshotProcessor(corSettings)

    @Test
    fun `user can correct upload snapshot`() = runTest {
        val ctx = EscalopContext(
            UserCommand.UPLOAD,
            workMode = WorkMode.STUB,
            requestId = RequestId(35),
            stubCase = Stubs.SUCCESS,
            userId = UserId("ABC"),
            settings = corSettings
        )
        processor.exec(ctx)
        assertTrue(ctx.response is DocumentUploadResponse)
        assertTrue ("Response status not true" ){
            val response = ctx.response as DocumentUploadResponse
            response.status
        }
        assertEquals("Success uploaded", (ctx.response as DocumentUploadResponse).message, "Message incorrect")
    }

    @Test
    fun `user can correct read snapshot`() = runTest {
        val ctx = EscalopContext(
            UserCommand.READ,
            workMode = WorkMode.STUB,
            requestId = RequestId(35),
            stubCase = Stubs.SUCCESS,
            userId = UserId("ABC"),
            settings = corSettings
        )
        processor.exec(ctx)
        assertTrue( ctx.response is SnapshotReadResponse )
        val snapshotReadResponse = ctx.response as SnapshotReadResponse
        assertEquals(1, snapshotReadResponse.snapshotId.get())
    }

    @Test
    fun `user can correct search snapshots`() = runTest {
        val ctx = EscalopContext(
            UserCommand.SEARCH,
            workMode = WorkMode.STUB,
            requestId = RequestId(35),
            stubCase = Stubs.SUCCESS,
            userId = UserId("ABC"),
            settings = corSettings
        )
        processor.exec(ctx)
        assertTrue( ctx.response is SnapshotSearchResponse)
        val snapshotSearchResponse = ctx.response as SnapshotSearchResponse
        assertEquals(1, snapshotSearchResponse.snapshotsMetaInfo.size)
        assertEquals(1, snapshotSearchResponse.snapshotsMetaInfo[0].snapshotId.get())
    }
}
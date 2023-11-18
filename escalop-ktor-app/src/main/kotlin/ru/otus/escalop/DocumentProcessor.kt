package ru.otus.escalop

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import ru.otus.cassandra.CassandraClientStub
import ru.otus.common.EsculapContext
import ru.otus.common.entity.Metric
import ru.otus.common.entity.Snapshot
import ru.otus.common.model.*
import ru.otus.entrails.ObjectSerializer
import ru.otus.entrails.PdfTextExtractorStub
import ru.otus.google.GoogleCalendarStub
import java.util.concurrent.atomic.AtomicInteger
import java.util.logging.Logger
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class DocumentProcessor(
    private val googleCalendar: GoogleCalendarStub,
    private val cassandraClientStub: CassandraClientStub
) {

    val logger: Logger = Logger.getLogger(this::class.java.name)
    private val id = AtomicInteger(0)


    @OptIn(ExperimentalEncodingApi::class)
    suspend fun exec(ctx: EsculapContext) {
        require(ctx.workMode == WorkMode.STUB) {
            "Currently working only in STUB mode."
        }
        when (ctx.command) {
            UserCommand.NONE -> {
                ctx.response = EmptyResponse()
            }

            UserCommand.UPLOAD -> {
                val request = ctx.userRequest as UploadDocumentRequest
                val decodedBytes = Base64.decode(request.fileBase64)
                val metricText: String = PdfTextExtractorStub.extract(documentType = request.documentType, decodedBytes)
                val metrics = ObjectSerializer.convert(metricText)
                val localDate: LocalDate = PdfTextExtractorStub.extractDate(decodedBytes)
                val snapshot = Snapshot(
                    id.getAndIncrement().toLong(), request.documentType.name, localDate,
                    metrics.associate { metric: Metric -> metric.name to metric.valueInfo.value }, request.documentName
                )

                googleCalendar.saveSnapshot(
                    ctx.userId.asString(),
                    snapshot.id,
                    ObjectSerializer.serializeSnapshotToString(snapshot)
                )
                ctx.response = DocumentUploadResponse(true, "Document successful uploaded")
            }

            UserCommand.READ -> {
                val request: ReadSnapshotRequest = ctx.userRequest as ReadSnapshotRequest
                if (ctx.stubCase == Stubs.SUCCESS) {
                    ctx.response = SnapshotReadResponse(
                        SnapshotId(1),
                        ObjectSerializer.serializeSnapshotToString(
                            Snapshot(
                                1,
                                DocumentType.BLOOD_GENERAL.name,
                                LocalDate(2023, 5, 5),
                                mapOf(Pair("m1", 33.3), Pair("m2", 32.5)),
                                "test snapshot"
                            )
                        )
                    )
                } else {
                    ctx.response = SnapshotReadResponse(
                        request.id,
                        googleCalendar.readSnapshot(ctx.userId.asString(), request.id.get())
                    )
                }
            }

            UserCommand.SEARCH -> {
                val request: SearchSnapshotRequest = ctx.userRequest as SearchSnapshotRequest
                logger.info("Пока реализован только поиск по наименованию")
                if (ctx.stubCase == Stubs.SUCCESS) {
                    ctx.response = SnapshotSearchResponse(
                        listOf(
                            SnapshotInfo(SnapshotId(1), Clock.System.now(), "test 1"),
                            SnapshotInfo(SnapshotId(2), Clock.System.now(), "test 2")
                        )
                    )
                } else {
                    ctx.response = SnapshotSearchResponse(
                        googleCalendar.listNamesContainsSubStr(
                            ctx.userId.asString(),
                            request.userFilterRequest.namePart!!
                        )
                    )
                }
            }
        }
    }
}

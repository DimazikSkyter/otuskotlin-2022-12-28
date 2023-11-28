package ru.otus.escalop.biz

import kotlinx.datetime.LocalDate
import ru.otus.common.EscalopCorSettings
import ru.otus.common.EscalopContext
import ru.otus.common.calendar.*
import ru.otus.common.entity.Snapshot
import ru.otus.common.logging.ILogWrapper
import ru.otus.common.model.*
import ru.otus.common.repo.DbSnapshotCreateRequest
import ru.otus.common.repo.DbSnapshotFilterRequest
import ru.otus.common.repo.ISnapshotRepository
import ru.otus.entrails.ObjectSerializer
import ru.otus.entrails.PdfTextExtractorStub
import java.util.concurrent.atomic.AtomicInteger
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


class SnapshotProcessor(
    private val corSettings: EscalopCorSettings,
    private val logger: ILogWrapper,
    private val calendarClient: ICalendarClient,
    private val snapshotRepository: ISnapshotRepository
) {
    constructor(corSettings: EscalopCorSettings) : this(
        corSettings,
        corSettings.loggerProvider.logger(SnapshotProcessor::class),
        corSettings.calendarClient(),
        corSettings.snapshotRepository()
    )

    private val id = AtomicInteger(0)


    @OptIn(ExperimentalEncodingApi::class)
    suspend fun exec(ctx: EscalopContext) {
        require(ctx.workMode == WorkMode.STUB) {
            "Currently working only in STUB mode."
        }
        when (ctx.command) {
            UserCommand.NONE -> {
                ctx.response = EmptyResponse()
            }

            UserCommand.UPLOAD -> {
                try {
                    val request = ctx.userRequest as UploadDocumentRequest
                    val decodedBytes = Base64.decode(request.fileBase64)
                    val metricText: String = PdfTextExtractorStub.extract(request.documentType, decodedBytes)
                    val metrics = ObjectSerializer.convert(metricText)
                    val localDate: LocalDate = PdfTextExtractorStub.extractDate(decodedBytes)
                    val id = id.getAndIncrement().toLong()
                    val snapshot = Snapshot(
                        id,
                        request.documentType,
                        localDate,
                        metrics.map { it.name },
                        request.documentName
                    )

                    val dbSnapshotCreateRequest = DbSnapshotCreateRequest(
                        SnapshotId(id),
                        snapshot,
                        ctx.userId.asString(),
                        false
                    )
                    if (!snapshotRepository.createSnapshot(dbSnapshotCreateRequest).success) {
                        throw RuntimeException("Failed to generate new snapshot and store it to repository")
                    }

                    if (calendarClient.writeSnapshot(
                            WriteRequest(
                                CalendarSnapshot(
                                    ctx.userId.asString(),
                                    localDate,
                                    request.documentType,
                                    request.documentName,
                                    metrics
                                )
                            )
                        ).resultStatus == CalendarResponseStatus.SUCCESS
                    ) {
                        dbSnapshotCreateRequest.storeInCalendar = true
                        snapshotRepository.updateSnapshot(dbSnapshotCreateRequest)
                    }

                    ctx.response = DocumentUploadResponse(true, "Document successful uploaded")
                } catch (e: Exception) {
                    logger.error(
                        "Failed to upload snapshot",
                        e = e,
                        objs = mapOf(Pair("userRequest", ctx.userRequest))
                    )
                    ctx.response = DocumentUploadResponse(false, "Document uploaded failed")
                    ctx.errors.add(OperationError("", "", "", "Failed to upload snapshot"))
                }
            }

            UserCommand.READ -> {
                try {
                    val request: ReadSnapshotRequest = ctx.userRequest as ReadSnapshotRequest
                    ctx.response = SnapshotReadResponse(
                        request.id,
                        ObjectSerializer.serializeSnapshotToString(
                            calendarClient.readSnapshot(request.id.get())!!.toSnapshot(request.id.get())
                        )
                    )
                } catch (e: Exception) {
                    logger.error("Failed to read snapshot by snapshotId", e = e, objs = mapOf(Pair("snapshotId", ctx.requestId.asString())))
                    ctx.response = SnapshotReadResponse(
                        SnapshotId.NONE,
                        null
                    )
                    ctx.errors.add(OperationError("", "", "", "Failed to read snapshot"))
                }
            }

            UserCommand.SEARCH -> {
                var userFilterRequest: UserFilterRequest = UserFilterRequest(null, null, null)
                try {
                    val request: SearchSnapshotRequest = ctx.userRequest as SearchSnapshotRequest
                    userFilterRequest = request.userFilterRequest
                    val dbSnapshotResponse = snapshotRepository.searchSearchSnapshot(
                        DbSnapshotFilterRequest(
                            ctx.userId.asString(),
                            userFilterRequest
                        )
                    )
                    ctx.response = SnapshotSearchResponse(dbSnapshotResponse.snapshotsInfo ?: listOf())
                } catch (e: Exception) {
                    logger.error("Failed to search snapshot with filters", e = e, objs = mapOf(Pair("filters", userFilterRequest)))
                    ctx.response = SnapshotSearchResponse(listOf())
                    ctx.errors.add(OperationError("", "", "", "Failed to filter snapshot by filter parameters"))
                }
            }
        }
    }
}

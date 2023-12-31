package ru.otus.escalop.biz.workers

import kotlinx.datetime.LocalDate
import ru.otus.common.EscalopContext
import ru.otus.common.entity.Snapshot
import ru.otus.common.model.*
import ru.otus.common.repo.DbSnapshotCreateRequest
import ru.otus.entrails.ObjectSerializer
import ru.otus.entrails.PdfTextExtractorStub
import ru.otus.escalop.ICorChainDsl
import ru.otus.escalop.worker
import java.util.concurrent.atomic.AtomicLong
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

val GLOBAL_INDEX = AtomicLong(0)

@OptIn(ExperimentalEncodingApi::class)
fun ICorChainDsl<EscalopContext>.generateSnapshot(title: String) = worker {
    this.title = title
    description = "Подготовка объекта к сохранению в базе данных"
    on { state == RequestState.RUNNING }
    handle {
        val request = userRequest as UploadDocumentRequest
        val decodedBytes = Base64.decode(request.fileBase64)
        val metricText: String =
            PdfTextExtractorStub.extract(request.documentType, decodedBytes) // перенести в отдельный стейт
        val metrics = ObjectSerializer.convert(metricText)
        val localDate: LocalDate = PdfTextExtractorStub.extractDate(decodedBytes) // перенести в отдельный стейт
        val id = GLOBAL_INDEX.getAndIncrement()
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
            userId.asString(),
            false
        )

        val prepareSnapshotInCassandraState = PrepareSnapshotInCassandraState(
            id,
            "prepare snapshot snapshot",
            "Сохранение снапшота",
            userId,
            dbSnapshotCreateRequest,
            metrics
        )
        (this.states as MutableList<EscalopState>).add(prepareSnapshotInCassandraState)
    }
}

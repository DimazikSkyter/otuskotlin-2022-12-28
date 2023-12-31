package ru.otus.escalop.biz.workers

import kotlinx.datetime.LocalDate
import ru.otus.common.EscalopContext
import ru.otus.common.entity.Snapshot
import ru.otus.common.model.*
import ru.otus.common.repo.DbSnapshotCreateRequest
import ru.otus.common.repo.DbSnapshotFilterRequest
import ru.otus.entrails.ObjectSerializer
import ru.otus.entrails.PdfTextExtractorStub
import ru.otus.escalop.ICorChainDsl
import ru.otus.escalop.worker
import java.util.concurrent.atomic.AtomicLong
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


fun ICorChainDsl<EscalopContext>.prepareDbSnapshotFilterRequest(title: String) = worker {
    this.title = title
    description = "Подготовка объекта к сохранению в базе данных"
    on { state == RequestState.RUNNING }
    handle {
        val request = userRequest as SearchSnapshotRequest
        val filterRequest = DbSnapshotFilterRequest(
            userId.asString(),
            request.userFilterRequest
        )
        (states as MutableList<EscalopState>).add(PrepareSearchRequestState(GLOBAL_INDEX.getAndIncrement(), title, description, userId, filterRequest))
    }
}

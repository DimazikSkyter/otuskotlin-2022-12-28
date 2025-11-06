package ru.escalop.escalop.biz.workers.stubs

import kotlinx.datetime.Clock
import ru.escalop.common.EscalopContext
import ru.escalop.common.model.*
import ru.escalop.escalop.ICorChainDsl
import ru.escalop.escalop.worker
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


fun ICorChainDsl<EscalopContext>.stubCreateSuccessResponseOnUpload(title: String) = worker {
    this.title = title
    on { stubCase == Stubs.SUCCESS && state == RequestState.RUNNING }
    handle {
        state = RequestState.FINISH
        response = DocumentUploadResponse(true, "Success uploaded")
    }
}

@OptIn(ExperimentalEncodingApi::class)
fun ICorChainDsl<EscalopContext>.stubCreateSuccessResponseOnRead(title: String) = worker {
    this.title = title
    on { stubCase == Stubs.SUCCESS && state == RequestState.RUNNING }
    handle {
        state = RequestState.FINISH
        response = SnapshotReadResponse(SnapshotId(1), Base64.encode("SnapshotData".toByteArray()))
    }
}


fun ICorChainDsl<EscalopContext>.stubCreateSuccessResponseOnSearch(title: String) = worker {
    this.title = title
    on { stubCase == Stubs.SUCCESS && state == RequestState.RUNNING }
    handle {
        state = RequestState.FINISH
        response = SnapshotSearchResponse(
            listOf(
                SnapshotInfo(
                    SnapshotId(1), Clock.System.now(), "stub snapshot name"
                )
            )
        )
    }
}
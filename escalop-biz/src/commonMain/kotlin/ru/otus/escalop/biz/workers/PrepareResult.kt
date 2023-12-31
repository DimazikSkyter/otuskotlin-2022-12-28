package ru.otus.escalop.biz.workers

import ru.otus.common.EscalopContext
import ru.otus.common.model.*
import ru.otus.entrails.ObjectSerializer
import ru.otus.escalop.ICorChainDsl
import ru.otus.escalop.worker


fun ICorChainDsl<EscalopContext>.prepareUploadResult(title: String) = worker {
    this.title = title
    description = "Формирование ответа пользователю по загрузки документа"
    on { state == RequestState.RUNNING && states.any { escalopState -> escalopState is UploadFinishState } }
    handle {
        response = DocumentUploadResponse(true, "Document successful uploaded")
    }
}


fun ICorChainDsl<EscalopContext>.prepareReadSnapshotResult(title: String) = worker {
    this.title = title
    description = "Формирование ответа по чтению снапшота по идентификатору"
    on { state == RequestState.RUNNING && states.any { escalopState -> escalopState is SnapshotReadState } }
    handle {
        val request: ReadSnapshotRequest = userRequest as ReadSnapshotRequest
        val snapshotReadState = states.first { escalopState -> escalopState is SnapshotReadState } as SnapshotReadState
        response = SnapshotReadResponse(
            request.id,
            snapshotReadState.snapshot?.let { ObjectSerializer.serializeSnapshotToString(it) }
        )
    }
}


fun ICorChainDsl<EscalopContext>.prepareSearchSnapshotResult(title: String) = worker {
    this.title = title
    description = "Формирование ответа по поиску снапшотов по предикату"
    on { state == RequestState.RUNNING && states.any { escalopState -> escalopState is SnapshotsInfoSearchResultState } }
    handle {
        val snapshotsInfoSearchResultState =
            states.first { escalopState -> escalopState is SnapshotsInfoSearchResultState } as SnapshotsInfoSearchResultState
        response = SnapshotSearchResponse(snapshotsInfoSearchResultState.dbSnapshotsResponse.snapshotsInfo ?: listOf())
    }
}

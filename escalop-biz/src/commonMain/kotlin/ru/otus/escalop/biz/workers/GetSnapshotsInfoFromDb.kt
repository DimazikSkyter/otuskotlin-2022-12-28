package ru.otus.escalop.biz.workers

import ru.otus.common.EscalopContext
import ru.otus.common.model.EscalopState
import ru.otus.common.model.PrepareSearchRequestState
import ru.otus.common.model.RequestState
import ru.otus.common.model.SnapshotsInfoSearchResultState
import ru.otus.common.repo.DbSnapshotFilterRequest
import ru.otus.common.repo.DbSnapshotsResponse
import ru.otus.escalop.ICorChainDsl
import ru.otus.escalop.worker


fun ICorChainDsl<EscalopContext>.getSnapshotsInfoByDbSnapshotFilterRequest(title: String) = worker {
    this.title = title
    description = "Подготовка объекта к сохранению в базе данных"
    on { state == RequestState.RUNNING && states.any { escalopState -> escalopState is PrepareSearchRequestState } }
    handle {
        val prepareSearchRequestState =
            states.first { escalopState -> escalopState is PrepareSearchRequestState } as PrepareSearchRequestState
        val dbSnapshotResponse: DbSnapshotsResponse = snapshotRepository.searchSearchSnapshot(
            DbSnapshotFilterRequest(
                userId.asString(),
                (prepareSearchRequestState).snapshotFilterRequest.filter
            )
        )
        (states as MutableList<EscalopState>).add(
            SnapshotsInfoSearchResultState(
                prepareSearchRequestState,
                title,
                description,
                dbSnapshotResponse
            )
        )
    }
}

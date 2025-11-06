package ru.escalop.escalop.biz.workers

import ru.escalop.common.EscalopContext
import ru.escalop.common.model.EscalopState
import ru.escalop.common.model.PrepareSearchRequestState
import ru.escalop.common.model.RequestState
import ru.escalop.common.model.SnapshotsInfoSearchResultState
import ru.escalop.common.repo.DbSnapshotFilterRequest
import ru.escalop.common.repo.DbSnapshotsResponse
import ru.escalop.escalop.ICorChainDsl
import ru.escalop.escalop.worker


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

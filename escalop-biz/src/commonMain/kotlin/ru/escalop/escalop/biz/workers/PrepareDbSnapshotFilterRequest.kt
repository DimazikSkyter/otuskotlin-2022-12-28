package ru.escalop.escalop.biz.workers

import ru.escalop.common.EscalopContext
import ru.escalop.common.model.*
import ru.escalop.common.repo.DbSnapshotFilterRequest
import ru.escalop.escalop.ICorChainDsl
import ru.escalop.escalop.worker


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

package ru.otus.escalop.biz.workers

import ru.otus.common.EscalopContext
import ru.otus.common.model.EmptyResponse
import ru.otus.common.model.RequestState
import ru.otus.escalop.ICorChainDsl
import ru.otus.escalop.worker


fun ICorChainDsl<EscalopContext>.emptyResponse(title: String) = worker {
    this.title = title
    handle {
        state = RequestState.FINISH
        response = EmptyResponse()
    }
}

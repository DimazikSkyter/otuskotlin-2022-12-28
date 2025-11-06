package ru.escalop.escalop.biz.workers

import ru.escalop.common.EscalopContext
import ru.escalop.common.model.EmptyResponse
import ru.escalop.common.model.RequestState
import ru.escalop.escalop.ICorChainDsl
import ru.escalop.escalop.worker


fun ICorChainDsl<EscalopContext>.emptyResponse(title: String) = worker {
    this.title = title
    handle {
        state = RequestState.FINISH
        response = EmptyResponse()
    }
}

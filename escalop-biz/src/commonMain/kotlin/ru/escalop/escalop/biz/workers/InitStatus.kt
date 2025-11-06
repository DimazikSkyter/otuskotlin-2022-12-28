package ru.escalop.escalop.biz.workers

import ru.escalop.common.EscalopContext
import ru.escalop.common.model.RequestState
import ru.escalop.escalop.ICorChainDsl
import ru.escalop.escalop.worker

fun ICorChainDsl<EscalopContext>.initStatus(title: String) = worker {
    this.title = title
    on { state == RequestState.NONE }
    handle { state = RequestState.RUNNING }
}
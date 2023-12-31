package ru.otus.escalop.biz.workers

import ru.otus.common.EscalopContext
import ru.otus.common.model.RequestState
import ru.otus.escalop.ICorChainDsl
import ru.otus.escalop.worker

fun ICorChainDsl<EscalopContext>.initStatus(title: String) = worker {
    this.title = title
    on { state == RequestState.NONE }
    handle { state = RequestState.RUNNING }
}
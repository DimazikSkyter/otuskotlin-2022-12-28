package ru.otus.escalop.biz.workers.validation

import ru.otus.common.EscalopContext
import ru.otus.common.model.RequestState
import ru.otus.escalop.ICorChainDsl
import ru.otus.escalop.chain


fun ICorChainDsl<EscalopContext>.validation(block: ICorChainDsl<EscalopContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == RequestState.RUNNING }
}

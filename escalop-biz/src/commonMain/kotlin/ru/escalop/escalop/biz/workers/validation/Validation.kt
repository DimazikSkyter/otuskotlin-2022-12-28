package ru.escalop.escalop.biz.workers.validation

import ru.escalop.common.EscalopContext
import ru.escalop.common.model.RequestState
import ru.escalop.escalop.ICorChainDsl
import ru.escalop.escalop.chain


fun ICorChainDsl<EscalopContext>.validation(block: ICorChainDsl<EscalopContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == RequestState.RUNNING }
}

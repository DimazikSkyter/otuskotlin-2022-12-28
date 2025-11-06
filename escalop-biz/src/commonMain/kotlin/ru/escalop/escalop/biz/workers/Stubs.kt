package ru.escalop.escalop.biz.workers

import ru.escalop.common.EscalopContext
import ru.escalop.common.model.RequestState
import ru.escalop.common.model.WorkMode
import ru.escalop.escalop.ICorChainDsl
import ru.escalop.escalop.chain


fun ICorChainDsl<EscalopContext>.stubs(title: String, block: ICorChainDsl<EscalopContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == WorkMode.STUB && state == RequestState.RUNNING }
}

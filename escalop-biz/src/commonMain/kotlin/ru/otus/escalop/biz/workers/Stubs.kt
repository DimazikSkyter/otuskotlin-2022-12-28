package ru.otus.escalop.biz.workers

import ru.otus.common.EscalopContext
import ru.otus.common.model.RequestState
import ru.otus.common.model.WorkMode
import ru.otus.escalop.ICorChainDsl
import ru.otus.escalop.chain


fun ICorChainDsl<EscalopContext>.stubs(title: String, block: ICorChainDsl<EscalopContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == WorkMode.STUB && state == RequestState.RUNNING }
}

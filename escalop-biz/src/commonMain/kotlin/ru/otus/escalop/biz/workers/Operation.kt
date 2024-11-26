package ru.otus.escalop.biz.workers

import ru.otus.common.EscalopContext
import ru.otus.common.model.RequestState
import ru.otus.common.model.UserCommand
import ru.otus.escalop.ICorChainDsl
import ru.otus.escalop.chain


fun ICorChainDsl<EscalopContext>.operation(title: String, command: UserCommand, block: ICorChainDsl<EscalopContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { this.command == command && state == RequestState.RUNNING }
}

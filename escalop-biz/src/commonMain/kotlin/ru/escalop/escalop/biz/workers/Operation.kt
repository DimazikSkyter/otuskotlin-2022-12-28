package ru.escalop.escalop.biz.workers

import ru.escalop.common.EscalopContext
import ru.escalop.common.model.RequestState
import ru.escalop.common.model.UserCommand
import ru.escalop.escalop.ICorChainDsl
import ru.escalop.escalop.chain


fun ICorChainDsl<EscalopContext>.operation(title: String, command: UserCommand, block: ICorChainDsl<EscalopContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { this.command == command && state == RequestState.RUNNING }
}

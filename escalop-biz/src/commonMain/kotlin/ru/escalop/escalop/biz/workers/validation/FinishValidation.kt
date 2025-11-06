package ru.escalop.escalop.biz.workers.validation

import ru.escalop.common.EscalopContext
import ru.escalop.common.model.EscalopState
import ru.escalop.common.model.RequestState
import ru.escalop.common.model.ValidateFinishState
import ru.escalop.escalop.ICorChainDsl
import ru.escalop.escalop.worker
import java.util.concurrent.atomic.AtomicLong

val atomicLong: AtomicLong = AtomicLong(0)

fun ICorChainDsl<EscalopContext>.finishValidation(title: String) = worker {
    this.title = title
    on { state == RequestState.RUNNING }
    handle {
        (states as MutableList<EscalopState>).add(
            ValidateFinishState(
                atomicLong.getAndIncrement(),
                title,
                "Валидация успешно пройдена",
                userId
            )
        )
    }
}
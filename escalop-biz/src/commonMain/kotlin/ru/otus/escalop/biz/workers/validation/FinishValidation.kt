package ru.otus.escalop.biz.workers.validation

import ru.otus.common.EscalopContext
import ru.otus.common.model.EscalopState
import ru.otus.common.model.RequestState
import ru.otus.common.model.ValidateFinishState
import ru.otus.escalop.ICorChainDsl
import ru.otus.escalop.worker
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
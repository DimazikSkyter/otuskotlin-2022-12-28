package ru.otus.escalop.biz.workers

import ru.otus.common.EscalopContext
import ru.otus.common.model.*
import ru.otus.escalop.ICorChainDsl
import ru.otus.escalop.worker


fun ICorChainDsl<EscalopContext>.uploadFinished(title: String) = worker {
    this.title = title
    description = "Подготовка объекта к сохранению в базе данных"
    on { state == RequestState.RUNNING && states.any { escalopState ->  escalopState is SnapshotHandleFinishedState} }
    handle {
        (states as MutableList<EscalopState>).add(UploadFinishState())
    }
}

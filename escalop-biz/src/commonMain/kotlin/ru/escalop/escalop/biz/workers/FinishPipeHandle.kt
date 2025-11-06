package ru.escalop.escalop.biz.workers

import ru.escalop.common.EscalopContext
import ru.escalop.common.model.*
import ru.escalop.escalop.ICorChainDsl
import ru.escalop.escalop.worker


fun ICorChainDsl<EscalopContext>.uploadFinished(title: String) = worker {
    this.title = title
    description = "Подготовка объекта к сохранению в базе данных"
    on { state == RequestState.RUNNING && states.any { escalopState ->  escalopState is SnapshotHandleFinishedState} }
    handle {
        (states as MutableList<EscalopState>).add(UploadFinishState())
    }
}

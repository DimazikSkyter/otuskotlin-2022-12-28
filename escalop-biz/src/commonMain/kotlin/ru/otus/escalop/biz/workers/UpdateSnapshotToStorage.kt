package ru.otus.escalop.biz.workers

import ru.otus.common.EscalopContext
import ru.otus.common.model.*
import ru.otus.escalop.ICorChainDsl
import ru.otus.escalop.worker

fun ICorChainDsl<EscalopContext>.updateSnapshotInformation(title: String) = worker {
    this.title = title
    description = "Подготовка объекта к сохранению в базе данных"
    on { state == RequestState.RUNNING && states[2] is SaveSnapshotInCalendarState }
    handle {
        val escalopStates = states as MutableList<EscalopState>
        val saveSnapshotInCalendarState = escalopStates[2] as SaveSnapshotInCalendarState
        if (!snapshotRepository.updateSnapshot(saveSnapshotInCalendarState.dbSnapshotCreateRequest).success) {
            state = RequestState.FAILING
            errors.add(OperationError("2", "system errors", "", "Failed to save snapshot ${saveSnapshotInCalendarState.id}", OperationError.Level.ERROR))
            response = DocumentUploadResponse(false, "ERROR")
        } else {
            escalopStates.add(SnapshotHandleFinishedState(saveSnapshotInCalendarState))
        }
    }
}

package ru.otus.escalop.biz.workers

import ru.otus.common.EscalopContext
import ru.otus.common.model.*
import ru.otus.escalop.ICorChainDsl
import ru.otus.escalop.worker

fun ICorChainDsl<EscalopContext>.saveSnapshotToStorage(title: String) = worker {
    this.title = title
    description = "Подготовка объекта к сохранению в базе данных"
    on { state == RequestState.RUNNING && states[0] is PrepareSnapshotInCassandraState }
    handle {
        val escalopStates = states as MutableList<EscalopState>
        val prepareState = escalopStates[0] as PrepareSnapshotInCassandraState
        if (!snapshotRepository.createSnapshot(prepareState.dbSnapshotCreateRequest).success) {
            state = RequestState.FAILING
            errors.add(OperationError("2", "system errors", "", "Failed to save snapshot ${prepareState.id}", OperationError.Level.ERROR))
            response = DocumentUploadResponse(false, "ERROR")
        } else {
            escalopStates.add(SaveSnapshotInCassandraState(GLOBAL_INDEX.get().toLong(), "Save snapshot", "Снапшот успешно сохранен", userId))
        }
    }
}

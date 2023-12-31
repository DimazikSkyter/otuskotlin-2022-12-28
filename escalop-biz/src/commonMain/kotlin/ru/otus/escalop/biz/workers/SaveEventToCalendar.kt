package ru.otus.escalop.biz.workers

import ru.otus.common.EscalopContext
import ru.otus.common.calendar.CalendarResponseStatus
import ru.otus.common.calendar.CalendarSnapshot
import ru.otus.common.calendar.WriteRequest
import ru.otus.common.model.*
import ru.otus.escalop.ICorChainDsl
import ru.otus.escalop.worker


fun ICorChainDsl<EscalopContext>.createCalendarEventWithSnapshotInformation(title: String) = worker {
    this.title = title
    description = "Подготовка объекта к сохранению в базе данных"
    on { state == RequestState.RUNNING && states[1] is SaveSnapshotInCassandraState }
    handle {
        val request = userRequest as UploadDocumentRequest
        val escalopStates = states as MutableList<EscalopState>
        val prepareState = escalopStates[0] as PrepareSnapshotInCassandraState
        val snapshot = prepareState.dbSnapshotCreateRequest.snapshot
        if (calendarClient.writeSnapshot(
                WriteRequest(
                    CalendarSnapshot(
                        userId.asString(),
                        snapshot.date,
                        request.documentType,
                        request.documentName,
                        prepareState.metrics
                    )
                )
            ).resultStatus == CalendarResponseStatus.SUCCESS) {
            state = RequestState.FAILING
            errors.add(OperationError("3", "systems errors", "", "Failed to save snapshot ${prepareState.id} in calendar", OperationError.Level.ERROR))
            response = DocumentUploadResponse(false, "ERROR")
        } else {
            escalopStates.add(SaveSnapshotInCalendarState(prepareState))
        }
    }
}

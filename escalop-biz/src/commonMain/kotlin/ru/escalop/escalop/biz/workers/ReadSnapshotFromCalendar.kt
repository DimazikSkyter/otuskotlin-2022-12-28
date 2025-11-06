package ru.escalop.escalop.biz.workers

import ru.escalop.common.EscalopContext
import ru.escalop.common.entity.Snapshot
import ru.escalop.common.model.EscalopState
import ru.escalop.common.model.ReadSnapshotRequest
import ru.escalop.common.model.RequestState
import ru.escalop.common.model.SnapshotReadState
import ru.escalop.escalop.ICorChainDsl
import ru.escalop.escalop.worker


fun ICorChainDsl<EscalopContext>.readSnapshotFromCalendar(title: String) = worker {
    this.title = title
    description = "Подготовка объекта к сохранению в базе данных"
    on { state == RequestState.RUNNING }
    handle {
        val readRequest = userRequest as ReadSnapshotRequest
        val snapshot: Snapshot = calendarClient.readSnapshot(readRequest.id.get())!!.toSnapshot(readRequest.id.get())
        (states as MutableList<EscalopState>).add(SnapshotReadState(GLOBAL_INDEX.getAndIncrement(), title,  description, userId, snapshot))
    }
}

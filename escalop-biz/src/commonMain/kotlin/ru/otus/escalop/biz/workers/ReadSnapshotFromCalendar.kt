package ru.otus.escalop.biz.workers

import ru.otus.common.EscalopContext
import ru.otus.common.entity.Snapshot
import ru.otus.common.model.EscalopState
import ru.otus.common.model.ReadSnapshotRequest
import ru.otus.common.model.RequestState
import ru.otus.common.model.SnapshotReadState
import ru.otus.escalop.ICorChainDsl
import ru.otus.escalop.worker
import java.util.concurrent.atomic.AtomicLong


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

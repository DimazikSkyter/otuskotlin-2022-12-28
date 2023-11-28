package ru.otus.common.calendar

import ru.otus.common.model.SnapshotId


data class WriteResponse (
    val snapshotId: SnapshotId?,
    val resultStatus: CalendarResponseStatus
)

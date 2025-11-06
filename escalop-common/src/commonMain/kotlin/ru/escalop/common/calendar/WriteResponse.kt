package ru.escalop.common.calendar

import ru.escalop.common.model.SnapshotId


data class WriteResponse (
    val snapshotId: SnapshotId?,
    val resultStatus: CalendarResponseStatus
)

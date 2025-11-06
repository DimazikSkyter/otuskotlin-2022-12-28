package ru.escalop.common.repo

import ru.escalop.common.model.SnapshotInfo


data class DbSnapshotCreateRequest (
    val snapshot: SnapshotInfo,
    var storeInCalendar: Boolean
) {
    constructor(dbSnapshotCreateRequest: DbSnapshotCreateRequest, storeInCalendar: Boolean) : this(
        dbSnapshotCreateRequest.snapshotId,
        dbSnapshotCreateRequest.snapshot,
        dbSnapshotCreateRequest.user,
        storeInCalendar
    )
}
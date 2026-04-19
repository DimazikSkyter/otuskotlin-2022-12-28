package ru.escalop.common.repo

import ru.escalop.common.model.Snapshot
import ru.escalop.common.model.SnapshotId
import ru.escalop.common.model.UserId


data class DbSnapshotCreateRequest (
    val snapshotId: SnapshotId,
    val user: String,
    val snapshot: Snapshot,
    var storeInCalendar: Boolean
) {
    constructor(dbSnapshotIdRequest: DbSnapshotCreateRequest, user: Boolean) : this(
        dbSnapshotIdRequest.snapshotId,
        "user",
        dbSnapshotIdRequest.snapshot,
        user
    )
}
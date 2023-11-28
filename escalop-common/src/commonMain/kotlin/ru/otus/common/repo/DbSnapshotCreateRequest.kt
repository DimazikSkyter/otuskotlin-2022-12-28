package ru.otus.common.repo

import ru.otus.common.entity.Snapshot
import ru.otus.common.model.SnapshotId


data class DbSnapshotCreateRequest (
    val snapshotId: SnapshotId,
    val snapshot: Snapshot,
    val user: String,
    var storeInCalendar: Boolean
)
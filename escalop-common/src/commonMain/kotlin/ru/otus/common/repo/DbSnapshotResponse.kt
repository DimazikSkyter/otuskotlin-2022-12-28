package ru.otus.common.repo

import ru.otus.common.entity.Snapshot
import ru.otus.common.model.SnapshotInfo

data class DbSnapshotResponse (
    val snapshot: Snapshot?
)

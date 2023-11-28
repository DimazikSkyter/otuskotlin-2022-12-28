package ru.otus.common.repo

import ru.otus.common.model.SnapshotInfo

data class DbSnapshotsResponse (
    val snapshotsInfo: List<SnapshotInfo>?
)

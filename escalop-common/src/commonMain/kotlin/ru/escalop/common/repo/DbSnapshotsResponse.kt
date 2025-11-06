package ru.escalop.common.repo

import ru.escalop.common.model.SnapshotInfo

data class DbSnapshotsResponse (
    val snapshotsInfo: List<SnapshotInfo>?
)

package ru.escalop.common.repo

import ru.escalop.common.entity.Snapshot

data class DbSnapshotResponse (
    val snapshot: Snapshot?
)

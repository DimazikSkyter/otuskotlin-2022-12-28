package ru.escalop.common.repo

import ru.escalop.common.model.Snapshot

data class DbSnapshotResponse (
    val snapshot: Snapshot?
)

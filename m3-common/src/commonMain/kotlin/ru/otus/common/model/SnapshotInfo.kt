package ru.otus.common.model

import kotlinx.datetime.Instant

data class SnapshotInfo (
    val snapshotId: SnapshotId,
    val date: Instant,
    val name: String
)

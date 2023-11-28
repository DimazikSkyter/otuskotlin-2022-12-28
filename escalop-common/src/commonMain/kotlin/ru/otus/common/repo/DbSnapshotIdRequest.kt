package ru.otus.common.repo

import ru.otus.common.model.SnapshotId

data class DbSnapshotIdRequest(
    val id: SnapshotId,
    val user: String
)

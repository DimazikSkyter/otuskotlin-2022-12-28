package ru.otus.common.model

data class SnapshotReadResponse(
    val snapshotId: SnapshotId,
    val snapshotData: String?
) : Response()
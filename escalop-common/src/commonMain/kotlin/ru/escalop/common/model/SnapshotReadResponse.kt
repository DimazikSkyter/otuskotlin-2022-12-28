package ru.escalop.common.model

data class SnapshotReadResponse(
    val snapshotId: SnapshotId,
    val snapshotData: String?
) : Response()
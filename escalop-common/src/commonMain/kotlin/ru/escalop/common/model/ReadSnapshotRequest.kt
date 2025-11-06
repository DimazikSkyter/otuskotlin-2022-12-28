package ru.escalop.common.model

data class ReadSnapshotRequest(
    val id: SnapshotId
) : UserRequest()

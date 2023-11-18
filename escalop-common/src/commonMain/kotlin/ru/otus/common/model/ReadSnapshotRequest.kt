package ru.otus.common.model

data class ReadSnapshotRequest(
    val id: SnapshotId
) : UserRequest()

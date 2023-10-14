package ru.otus.common.model

data class SnapshotSearchResponse(
    val snapshotsMetaInfo: List<SnapshotInfo>
) : Response()
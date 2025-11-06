package ru.escalop.common.model

data class SnapshotSearchResponse(
    val snapshotsMetaInfo: List<SnapshotInfo>
) : Response()
package ru.escalop.common.repo

import kotlinx.datetime.DateTimeUnit
import ru.escalop.common.model.SnapshotId

data class DbSnapshotIdRequest(
    val id: SnapshotId,
    val user: String,
    val date: DateTimeUnit,
    val type:

)

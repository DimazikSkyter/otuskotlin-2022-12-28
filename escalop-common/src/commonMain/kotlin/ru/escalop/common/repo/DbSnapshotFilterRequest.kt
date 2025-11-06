package ru.escalop.common.repo

import ru.escalop.common.model.UserFilterRequest

data class DbSnapshotFilterRequest(
    val user: String,
    val filter: UserFilterRequest
)
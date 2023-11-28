package ru.otus.common.repo

import ru.otus.common.model.UserFilterRequest

data class DbSnapshotFilterRequest(
    val user: String,
    val filter: UserFilterRequest
)
package ru.otus.common.model

import ru.otus.common.repo.DbSnapshotFilterRequest

data class PrepareSearchRequestState(
    override var id: Long,
    override var title: String = "",
    override var description: String = "",
    override var owner: UserId = UserId.NONE,
    var snapshotFilterRequest: DbSnapshotFilterRequest
) : EscalopState(id, title, description, owner)
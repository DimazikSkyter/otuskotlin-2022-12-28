package ru.escalop.common.model

import ru.escalop.common.repo.DbSnapshotFilterRequest

data class PrepareSearchRequestState(
    override var id: Long,
    override var title: String = "",
    override var description: String = "",
    override var owner: UserId = UserId.NONE,
    var snapshotFilterRequest: DbSnapshotFilterRequest
) : EscalopState(id, title, description, owner)
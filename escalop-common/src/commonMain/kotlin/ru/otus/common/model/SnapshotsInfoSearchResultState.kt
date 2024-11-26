package ru.otus.common.model

import ru.otus.common.repo.DbSnapshotsResponse

data class SnapshotsInfoSearchResultState(
    override var id: Long,
    override var title: String = "",
    override var description: String = "",
    override var owner: UserId = UserId.NONE,
    var dbSnapshotsResponse: DbSnapshotsResponse
) : EscalopState(id, title, description, owner) {
    constructor(
        prepareSearchRequestState: PrepareSearchRequestState,
        title: String,
        description: String,
        dbSnapshotsResponse: DbSnapshotsResponse
    ) : this(
        prepareSearchRequestState.id,
        title,
        description,
        prepareSearchRequestState.owner,
        dbSnapshotsResponse
    )
}
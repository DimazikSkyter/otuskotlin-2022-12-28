package ru.otus.common.model

import ru.otus.common.entity.Metric
import ru.otus.common.repo.DbSnapshotCreateRequest

data class SaveSnapshotInCalendarState(
    override var id: Long = 0L,
    override var title: String = "",
    override var description: String = "",
    override var owner: UserId = UserId.NONE,
    var dbSnapshotCreateRequest: DbSnapshotCreateRequest
) : EscalopState(id, title, description, owner) {
    constructor(prePareSnapshotInCassandraState: PrepareSnapshotInCassandraState) : this(
        prePareSnapshotInCassandraState.id,
        prePareSnapshotInCassandraState.title,
        prePareSnapshotInCassandraState.description,
        prePareSnapshotInCassandraState.owner,
        DbSnapshotCreateRequest(prePareSnapshotInCassandraState.dbSnapshotCreateRequest, true)
    )
}
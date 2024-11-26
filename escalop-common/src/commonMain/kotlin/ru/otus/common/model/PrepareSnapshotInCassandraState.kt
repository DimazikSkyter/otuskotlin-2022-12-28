package ru.otus.common.model

import ru.otus.common.entity.Metric
import ru.otus.common.repo.DbSnapshotCreateRequest

data class PrepareSnapshotInCassandraState(
    override var id: Long = 0L,
    override var title: String = "",
    override var description: String = "",
    override var owner: UserId = UserId.NONE,
    var dbSnapshotCreateRequest: DbSnapshotCreateRequest,
    var metrics: List<Metric>
) : EscalopState(id, title, description, owner)
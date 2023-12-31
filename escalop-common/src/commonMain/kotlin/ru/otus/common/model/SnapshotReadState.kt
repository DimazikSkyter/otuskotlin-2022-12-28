package ru.otus.common.model

import ru.otus.common.entity.Snapshot

data class SnapshotReadState (
    override var id: Long,
    override var title: String = "",
    override var description: String = "",
    override var owner: UserId = UserId.NONE,
    var snapshot: Snapshot? = null
) : EscalopState(id, title, description, owner)
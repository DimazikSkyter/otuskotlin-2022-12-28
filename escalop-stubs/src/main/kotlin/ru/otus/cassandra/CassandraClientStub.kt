package ru.otus.cassandra

import ru.otus.common.entity.Snapshot
import ru.otus.common.model.SnapshotId

class CassandraClientStub {

    private val cache: MutableMap<SnapshotId, Snapshot> = mutableMapOf()

    fun putSnapshot(snapshotId: SnapshotId, snapshot: Snapshot) {
        cache[snapshotId] = snapshot
    }

    fun getSnapshot(snapshotId: SnapshotId): Snapshot? {
        return cache[snapshotId]
    }
}
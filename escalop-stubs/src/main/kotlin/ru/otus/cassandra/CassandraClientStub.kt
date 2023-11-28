package ru.otus.cassandra

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.otus.common.entity.Snapshot
import ru.otus.common.model.SnapshotId
import ru.otus.common.model.SnapshotInfo
import ru.otus.common.repo.*
import ru.otus.entrails.ObjectSerializer

class CassandraClientStub(
    private val cache: MutableMap<String, MutableMap<SnapshotId, Snapshot>> = mutableMapOf()
) : ISnapshotRepository {


    override suspend fun createSnapshot(rq: DbSnapshotCreateRequest): DbSnapshotInfoResponse {
        cache.getOrPut(rq.user) { mutableMapOf() }[rq.snapshotId] = rq.snapshot
        return DbSnapshotInfoResponse(true)
    }

    override suspend fun updateSnapshot(rq: DbSnapshotCreateRequest): DbSnapshotInfoResponse {
        return cache[rq.user]?.let {
            it[rq.snapshotId] = rq.snapshot
            DbSnapshotInfoResponse(true)
        } ?: DbSnapshotInfoResponse(false)
    }

    override suspend fun readSnapshot(rq: DbSnapshotIdRequest): DbSnapshotResponse {
        return DbSnapshotResponse(cache[rq.user]?.get(rq.id))
    }

    override suspend fun searchSearchSnapshot(rq: DbSnapshotFilterRequest): DbSnapshotsResponse {
        val snapshotInfoList = cache[rq.user]?.filter { entry ->
            rq.filter.namePart?.let { entry.value.name.contains(it) } ?: true
                    && rq.filter.date?.let {
                val snapshotDate = entry.value.date
                snapshotDate.dayOfYear == it.dayOfYear && snapshotDate.year == it.year
            } ?: true
                    && rq.filter.type?.let { entry.value.type.name == it.name } ?: true
        }?.map { entry ->
            SnapshotInfo(
                entry.key,
                Instant.fromEpochMilliseconds(entry.value.date.toEpochDays().toLong() * 86400L * 1000L),
                entry.value.name
            )
        }
        return DbSnapshotsResponse(snapshotInfoList)
    }
}
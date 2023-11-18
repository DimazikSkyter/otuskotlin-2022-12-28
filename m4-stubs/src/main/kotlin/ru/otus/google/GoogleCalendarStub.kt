package ru.otus.google

import kotlinx.datetime.Instant
import ru.otus.common.entity.Snapshot
import ru.otus.common.model.SnapshotId
import ru.otus.common.model.SnapshotInfo
import ru.otus.entrails.ObjectSerializer

class GoogleCalendarStub {

    private val cache: MutableMap<String, MutableMap<Long, String>> = mutableMapOf()

    fun saveSnapshot(userId: String, snapshotId: Long, snapshot: String){
        val userMap: MutableMap<Long, String> = cache[userId] ?: cache.let {
            val newMap: MutableMap<Long, String> = mutableMapOf()
            cache[userId] = newMap
            newMap
        }
        userMap[snapshotId] = snapshot
    }

    fun readSnapshot(userId: String, snapshotId: Long): String? {
        return cache[userId]?.get(snapshotId)
    }

    fun listNamesContainsSubStr(userId: String, nameFilter: String): List<SnapshotInfo> {
        return cache[userId]?.filter { entry -> entry.value.contains(nameFilter) }?.map { entry ->
            val snapshot: Snapshot = ObjectSerializer.snapshotInfoFromStr(entry.value)
            SnapshotInfo(SnapshotId(snapshot.id), Instant.fromEpochMilliseconds(snapshot.date.toEpochDays().toLong() * 86400 * 1000), snapshot.name)
        } ?: listOf()
    }
}
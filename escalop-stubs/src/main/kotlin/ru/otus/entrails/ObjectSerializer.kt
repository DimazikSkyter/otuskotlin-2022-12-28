package ru.otus.entrails

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.otus.common.entity.Metric
import ru.otus.common.entity.Snapshot

object ObjectSerializer {

    fun convert(text: String): List<Metric> {
        return Json.decodeFromString<List<Metric>>(text)
    }

    fun serializeSnapshotToString(snapshot: Snapshot): String {
        return Json.encodeToString(snapshot)
    }

    fun snapshotInfoFromStr(str: String): Snapshot {
        return Json.decodeFromString<Snapshot>(str)
    }
}
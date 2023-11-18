package ru.otus.common.model

import kotlin.jvm.JvmInline

@JvmInline
value class SnapshotId (private val id: Long) {
    fun asString() = id.toString()

    fun get(): Long = id

    companion object {
        val NONE = SnapshotId(-1)
    }
}

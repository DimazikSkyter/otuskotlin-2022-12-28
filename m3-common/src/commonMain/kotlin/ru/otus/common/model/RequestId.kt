package ru.otus.common.model

import kotlin.jvm.JvmInline

@JvmInline
value class RequestId (private val id: Long) {

    fun asString() = id.toString()

    companion object {
        val NONE = RequestId(-1)
    }
}

package ru.otus.common.model

import kotlin.jvm.JvmInline

@JvmInline
value class UserId (private val id: String) {

    fun asString() = id.toString()

    companion object {
        val NONE = UserId("")
    }
}

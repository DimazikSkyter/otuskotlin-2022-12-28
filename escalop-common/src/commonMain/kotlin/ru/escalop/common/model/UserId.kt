package ru.escalop.common.model

import kotlin.jvm.JvmInline

@JvmInline
value class UserId (private val id: String) {

    fun asString() = id

    companion object {
        val NONE = UserId("")
    }
}

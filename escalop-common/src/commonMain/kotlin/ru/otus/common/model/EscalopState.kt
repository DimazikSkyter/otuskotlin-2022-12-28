package ru.otus.common.model

abstract class EscalopState (
    open var id: Long,
    open var title: String = "",
    open var description: String = "",
    open var owner: UserId = UserId.NONE
)
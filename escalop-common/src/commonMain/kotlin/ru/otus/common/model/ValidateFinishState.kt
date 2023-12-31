package ru.otus.common.model

data class ValidateFinishState(
    override var id: Long = 0L,
    override var title: String = "",
    override var description: String = "",
    override var owner: UserId = UserId.NONE
) : EscalopState(id, title, description, owner)
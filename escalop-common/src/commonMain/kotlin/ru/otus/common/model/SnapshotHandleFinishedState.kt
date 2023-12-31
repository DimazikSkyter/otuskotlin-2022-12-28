package ru.otus.common.model


data class SnapshotHandleFinishedState(
    override var id: Long = 0L,
    override var title: String = "",
    override var description: String = "",
    override var owner: UserId = UserId.NONE
) : EscalopState(id, title, description, owner) {
    constructor(saveSnapshotInCassandraState: SaveSnapshotInCalendarState) : this (
        saveSnapshotInCassandraState.id,
        "Обработка снапшота завершена",
        "Снапшот успешно загружен",
        saveSnapshotInCassandraState.owner
    )
}
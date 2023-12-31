package ru.otus.common.model

data class UploadFinishState(
    override var id: Long = 0L,
    override var title: String = "",
    override var description: String = "",
    override var owner: UserId = UserId.NONE
) : EscalopState(id, title, description, owner) {
    constructor(snapshotHandleFinishedState: SnapshotHandleFinishedState) : this(
        snapshotHandleFinishedState.id,
        snapshotHandleFinishedState.title,
        snapshotHandleFinishedState.description,
        snapshotHandleFinishedState.owner
    )
}
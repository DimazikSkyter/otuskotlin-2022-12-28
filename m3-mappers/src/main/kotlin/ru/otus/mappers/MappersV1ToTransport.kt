package ru.otus.mappers

import ru.otus.api.v1.models.*
import ru.otus.common.EsculapContext
import ru.otus.common.model.OperationError
import ru.otus.common.model.RequestState
import ru.otus.common.model.SnapshotInfo
import ru.otus.common.model.UserCommand
import ru.otus.exceptions.UnknownResponseCommand

fun EsculapContext.toTransport(): IResponse = when (val cmd = command) {
    UserCommand.UPLOAD -> toTransportUpload()
    UserCommand.READ -> toTransportRead()
    UserCommand.SEARCH -> toTransportSearch()
    else -> throw UnknownResponseCommand(cmd)
}

fun EsculapContext.toTransportSearch() = SnapshotSearchResponse(
    userId = this.userId.asString().takeIf { it.isNotBlank() },
    requestId = this.requestId.asString(),
    result = if (this.state == RequestState.FINISH) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = this.errors.map { it.toError() }.toList(),
    list = (this.response as ru.otus.common.model.SnapshotSearchResponse).snapshotsMetaInfo.map { it.toSnapshotListObject() }
        .toList()
)

fun SnapshotInfo.toSnapshotListObject(): SnapshotListObject = SnapshotListObject(
    id = this.snapshotId.get().toBigDecimal(), date = this.date.toString(), name = this.name
)

fun EsculapContext.toTransportRead() = SnapshotReadResponse(
    userId = this.userId.asString().takeIf { it.isNotBlank() },
    requestId = this.requestId.asString(),
    result = if (this.state == RequestState.FINISH) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = this.errors.map { it.toError() }.toList(),
    metadata = SnapshotMetaDataObject(
        (this.response as ru.otus.common.model.SnapshotReadResponse).snapshotId.get().toBigDecimal()
    ),
    snapshot = (this.response as ru.otus.common.model.SnapshotReadResponse).snapshotData,
    permissions = setOf(SnapshotPermissions.PRIVATE)

)

fun EsculapContext.toTransportUpload(): IResponse = DocumentUploadResponse(
    userId = this.userId.asString().takeIf { it.isNotBlank() },
    requestId = this.requestId.asString(),
    result = this.state.toResult(),
    errors = this.errors.map { it.toError() }.toList(),
    status = this.state == RequestState.FINISH,
    message = message(this.state)
)

private fun message(state: RequestState): String {
    return if (state == RequestState.FINISH) "File successful upload" else if (state == RequestState.RUNNING) "" else "Failed to load file"
}

private fun OperationError.toError(): Error {
    return Error(this.code, this.group, this.field, this.message)
}

private fun RequestState.toResult(): ResponseResult {
    return when (this) {
        RequestState.NONE -> ResponseResult.ERROR
        RequestState.FINISH -> ResponseResult.SUCCESS
        RequestState.FAILING -> ResponseResult.ERROR
        RequestState.RUNNING -> ResponseResult.IN_PROGRESS
    }
}
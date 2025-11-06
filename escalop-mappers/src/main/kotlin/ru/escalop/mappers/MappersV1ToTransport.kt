package ru.escalop.mappers

import ru.escalop.api.v1.models.*
import ru.escalop.common.EscalopContext
import ru.escalop.common.model.OperationError
import ru.escalop.common.model.RequestState
import ru.escalop.common.model.SnapshotInfo
import ru.escalop.common.model.UserCommand
import ru.escalop.exceptions.UnknownResponseCommand

fun EscalopContext.toTransport(): IResponse = when (val cmd = command) {
    UserCommand.UPLOAD -> toTransportUpload()
    UserCommand.READ -> toTransportRead()
    UserCommand.SEARCH -> toTransportSearch()
    else -> throw UnknownResponseCommand(cmd)
}

fun EscalopContext.toTransportSearch() = SnapshotSearchResponse(
    userId = this.userId.asString().takeIf { it.isNotBlank() },
    requestId = this.requestId.asString(),
    result = if (this.state == RequestState.FINISH) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = this.errors.map { it.toError() }.toList(),
    list = (this.response as ru.escalop.common.model.SnapshotSearchResponse).snapshotsMetaInfo.map { it.toSnapshotListObject() }
        .toList()
)

fun SnapshotInfo.toSnapshotListObject(): SnapshotListObject = SnapshotListObject(
    id = this.snapshotId.get().toBigDecimal(), date = this.date.toString(), name = this.name
)

fun EscalopContext.toTransportRead() = SnapshotReadResponse(
    userId = this.userId.asString().takeIf { it.isNotBlank() },
    requestId = this.requestId.asString(),
    result = if (this.state == RequestState.FINISH) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = this.errors.map { it.toError() }.toList(),
    metadata = SnapshotMetaDataObject(
        (this.response as ru.escalop.common.model.SnapshotReadResponse).snapshotId.get().toBigDecimal()
    ),
    snapshot = (this.response as ru.escalop.common.model.SnapshotReadResponse).snapshotData,
    permissions = setOf(SnapshotPermissions.PRIVATE)

)

fun EscalopContext.toTransportUpload(): IResponse = DocumentUploadResponse(
    userId = this.userId.asString().takeIf { it.isNotBlank() },
    requestId = this.requestId.asString(),
    result = this.state.toResult(),
    errors = this.errors.map { it.toError() }.toList(),
    status = (this.response as ru.escalop.common.model.DocumentUploadResponse).status,
    message = (this.response as ru.escalop.common.model.DocumentUploadResponse).message
)



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

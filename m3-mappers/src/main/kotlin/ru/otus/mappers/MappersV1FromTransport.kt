package ru.otus.mappers

import kotlinx.datetime.Clock
import ru.otus.api.v1.models.*
import ru.otus.common.EsculapContext
import ru.otus.common.model.*
import ru.otus.common.model.DocumentType
import ru.otus.exceptions.UnknownRequestClass

fun EsculapContext.fromTransport(request: IRequest) = when(request) {
    is DocumentUploadRequest -> fromTransport(request)
    is SnapshotReadRequest -> fromTransport(request)
    is SnapshotSearchRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun IRequest?.requestId() = this?.requestId?.let { RequestId(it.toLong()) } ?: RequestId.NONE
private fun IRequest?.userId() = this?.userId?.let { UserId(it) } ?: UserId.NONE

fun EsculapContext.fromTransport(request: SnapshotSearchRequest) {
    command = UserCommand.SEARCH
    requestId = request.requestId()
    timeStart = Clock.System.now()
    userRequest = request.snapshot?.toInternal() ?: EmptyUserRequest()
    userId = request.userId()
    workMode = WorkMode.PROD
    stubCase = Stubs.NONE
}

fun EsculapContext.fromTransport(request: SnapshotReadRequest) {
    command = UserCommand.READ
    requestId = request.requestId()
    timeStart = Clock.System.now()
    userRequest = request.snapshot?.toInternal() ?: EmptyUserRequest()
    userId = request.userId()
    workMode = WorkMode.PROD
    stubCase = Stubs.NONE
}
fun EsculapContext.fromTransport(request: DocumentUploadRequest) {
    command = UserCommand.UPLOAD
    requestId = request.requestId()
    timeStart = Clock.System.now()
    userRequest = request.uploadMetaData?.toInternal() ?: EmptyUserRequest()
    userId = request.userId()
    workMode = WorkMode.PROD
    stubCase = Stubs.NONE
}
private fun SnapshotSearchFilter.toInternal(): SearchSnapshotRequest = SearchSnapshotRequest(
    userFilterRequest = this.searchString?.let { UserFilterRequest(it) } ?: UserFilterRequest.NONE
)

private fun SnapshotReadObject.toInternal(): ReadSnapshotRequest = ReadSnapshotRequest (
    id = this.id?.let { SnapshotId( it.toLong() ) } ?: SnapshotId.NONE
)

private fun DocumentUploadObject.toInternal(): UploadDocumentRequest = UploadDocumentRequest(
    documentName = this.documentName ?: "",
    documentType = this.documentType?.let { getDocumentType(it) } ?: DocumentType.UNDEFINED,
    fileBase64 = this.file ?: ""
)

fun getDocumentType(documentType: ru.otus.api.v1.models.DocumentType): DocumentType {
    return when(documentType) {
        ru.otus.api.v1.models.DocumentType.GENERAL -> DocumentType.BLOOD_GENERAL
        ru.otus.api.v1.models.DocumentType.CHEMISTRY -> DocumentType.BLOOD_CHEMISTRY
    }
}

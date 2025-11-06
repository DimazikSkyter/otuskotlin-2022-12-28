package ru.escalop.escalop.biz.workers.validation

import ru.escalop.common.EscalopContext
import ru.escalop.common.errors.errorValidation
import ru.escalop.common.errors.fail
import ru.escalop.common.model.DocumentType
import ru.escalop.common.model.UploadDocumentRequest
import ru.escalop.escalop.ICorChainDsl
import ru.escalop.escalop.worker

fun ICorChainDsl<EscalopContext>.validateDocumentFormat(title: String) = worker {
    this.title = title
    on {
        val request: UploadDocumentRequest = this.userRequest as UploadDocumentRequest
        validateBody(request.documentType, request.fileBase64)
    }
    handle {
        fail(
            errorValidation(
                field = "id",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}

fun validateBody(documentType: DocumentType, fileBase64: String): Boolean {
    return true
}

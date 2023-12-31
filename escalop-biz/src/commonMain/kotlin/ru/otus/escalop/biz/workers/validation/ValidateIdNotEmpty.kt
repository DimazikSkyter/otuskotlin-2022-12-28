package ru.otus.escalop.biz.workers.validation

import ru.otus.common.EscalopContext
import ru.otus.common.errors.errorValidation
import ru.otus.common.errors.fail
import ru.otus.common.model.DocumentType
import ru.otus.common.model.UploadDocumentRequest
import ru.otus.escalop.ICorChainDsl
import ru.otus.escalop.worker

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

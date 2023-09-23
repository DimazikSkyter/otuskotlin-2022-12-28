package ru.otus.common.model

data class UploadDocumentRequest (
    var documentName: String,
    var documentType: DocumentType,
    var fileBase64: String
) : UserRequest()
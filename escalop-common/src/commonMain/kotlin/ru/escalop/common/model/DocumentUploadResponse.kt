package ru.escalop.common.model

data class DocumentUploadResponse(
    val status: Boolean,
    val message: String
): Response() {
}
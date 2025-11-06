package ru.escalop.exceptions

class UndefinedDocumentTypeException(override val message: String): RuntimeException(message) {
}
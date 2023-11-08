package ru.otus.exceptions

class UndefinedDocumentTypeException(override val message: String): RuntimeException(message) {
}
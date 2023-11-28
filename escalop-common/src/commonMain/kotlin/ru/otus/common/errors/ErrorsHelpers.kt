package ru.otus.common.errors

import ru.otus.common.model.OperationError

fun Throwable.asOperationError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = OperationError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this
)
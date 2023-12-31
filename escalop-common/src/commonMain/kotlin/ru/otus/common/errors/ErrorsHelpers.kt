package ru.otus.common.errors

import ru.otus.common.EscalopContext
import ru.otus.common.model.OperationError
import ru.otus.common.model.RequestState

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

fun EscalopContext.addError(vararg error: OperationError) = errors.addAll(error)
fun EscalopContext.fail(error: OperationError) {
    addError(error)
    state = RequestState.FAILING
}

fun errorAdministration(
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    field: String = "",
    violationCode: String,
    description: String,
    exception: Exception? = null,
    level: OperationError.Level = OperationError.Level.ERROR,
) = OperationError(
    field = field,
    code = "administration-$violationCode",
    group = "administration",
    message = "Microservice management error: $description",
    level = level,
    exception = exception,
)


fun errorValidation(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String,
    level: OperationError.Level = OperationError.Level.ERROR,
) = OperationError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)
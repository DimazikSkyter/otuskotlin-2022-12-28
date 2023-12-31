package ru.otus.escalop.helpers

import kotlinx.datetime.Clock
import ru.otus.common.EscalopContext
import ru.otus.common.errors.asOperationError
import ru.otus.common.model.RequestState
import ru.otus.escalop.settings.EscalopAppSettings
import kotlin.reflect.KClass

suspend inline fun <T> EscalopAppSettings.controllerHelper(
    crossinline getRequest: suspend EscalopContext.() -> Unit,
    crossinline toResponse: suspend EscalopContext.() -> T,
    clazz: KClass<*>,
    logId: String,
): T {
    val logger = corSettings.loggerProvider.logger(clazz)
    val ctx = EscalopContext(
        timeStart = Clock.System.now(),
    )
    return try {
        logger.doWithLogging(logId) {
            ctx.getRequest()
            processor.exec(ctx)
            logger.info(
                msg = "Request $logId processed for ${clazz.simpleName}", marker = "BIZ", data = ctx
            )
            ctx.toResponse()
        }
    } catch (e: Throwable) {
        logger.doWithLogging("$logId-failure") {
            ctx.state = RequestState.FAILING
            ctx.errors.add(e.asOperationError())
            processor.exec(ctx)
            ctx.toResponse()
        }
    }
}
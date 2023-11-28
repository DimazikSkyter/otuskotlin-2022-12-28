package ru.otus.escalop

import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.datetime.Clock
import ru.otus.api.v1.models.IRequest
import ru.otus.common.EscalopContext
import ru.otus.common.errors.asOperationError
import ru.otus.common.model.RequestState
import ru.otus.common.model.UserCommand.*
import ru.otus.escalop.settings.EscalopAppSettings
import ru.otus.mapper.apiV1RequestDeserialize
import ru.otus.mapper.apiV1ResponseSerialize
import ru.otus.mappers.fromTransport
import ru.otus.mappers.toTransport
import kotlin.reflect.KClass

val sessions = mutableSetOf<WebSocketSession>()

private val clazz: KClass<*> = WebSocketSession::wsHandlerV1::class
suspend fun WebSocketSession.wsHandlerV1(escalopAppSettings: EscalopAppSettings) {
    sessions.add(this)

    incoming.receiveAsFlow().mapNotNull { itFrame ->
        val frame = itFrame as? Frame.Text ?: return@mapNotNull
        try {
            escalopAppSettings.controllerHelper(
                { fromTransport(apiV1RequestDeserialize<IRequest>(frame.readText())) },
                {
                    val result = apiV1ResponseSerialize(toTransport())
                    // If change request, response is sent to everyone
                    if (isUpdatableCommand()) {
                        sessions.forEach {
                            if (it.isActive) it.send(Frame.Text(result))
                        }
                    } else {
                        outgoing.send(Frame.Text(result))
                    }
                },
                clazz,
                "wsHandlerV1-message",
            )
        } catch (_: ClosedReceiveChannelException) {
            sessions.clear()
        }

    }.collect {}
}

fun EscalopContext.isUpdatableCommand() = this.command in listOf(UPLOAD, READ, SEARCH)

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
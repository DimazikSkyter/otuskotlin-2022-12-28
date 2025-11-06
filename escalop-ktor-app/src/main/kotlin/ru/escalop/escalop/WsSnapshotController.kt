package ru.escalop.escalop

import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import ru.escalop.api.v1.models.IRequest
import ru.escalop.common.EscalopContext
import ru.escalop.common.model.UserCommand.*
import ru.escalop.escalop.helpers.controllerHelper
import ru.escalop.escalop.settings.EscalopAppSettings
import ru.escalop.mapper.apiV1RequestDeserialize
import ru.escalop.mapper.apiV1ResponseSerialize
import ru.escalop.mappers.fromTransport
import ru.escalop.mappers.toTransport
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


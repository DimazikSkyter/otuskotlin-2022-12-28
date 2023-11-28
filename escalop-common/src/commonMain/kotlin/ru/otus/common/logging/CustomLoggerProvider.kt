package ru.otus.common.logging

import kotlin.reflect.KClass
import kotlin.reflect.KFunction

class CustomLoggerProvider(
    private val provider: (String) -> ILogWrapper = { ILogWrapper.DEFAULT }
) {
    fun logger(loggerId: String) = provider(loggerId)
    fun logger(clazz: KClass<*>) = provider(clazz.qualifiedName ?: clazz.simpleName ?: "(unknown)")

    fun logger(function: KFunction<*>) = provider(function.name)
}

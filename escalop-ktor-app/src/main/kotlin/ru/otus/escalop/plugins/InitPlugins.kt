package ru.otus.escalop.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.util.pipeline.*
import ru.otus.escalop.settings.EscalopAppSettings

fun Application.initPlugins(appSettings: EscalopAppSettings = initAppSettings()) {
    installIfNotContains(Routing)
    installIfNotContains(WebSockets)

    installIfNotContains(CORS) {
        allowNonSimpleContentTypes = true
        allowSameOrigin = true
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Get)
        allowHeader("*")
        appSettings.appUrls.forEach {
            val split = it.split("://")
            println("$split")
            when (split.size) {
                2 -> allowHost(
                    split[1].split("/")[0]/*.apply { log(module = "app", msg = "COR: $this") }*/,
                    listOf(split[0])
                )

                1 -> allowHost(
                    split[0].split("/")[0]/*.apply { log(module = "app", msg = "COR: $this") }*/,
                    listOf("http", "https")
                )
            }
        }
    }
    installIfNotContains(CachingHeaders)
    installIfNotContains(AutoHeadResponse)
}

fun <P : Pipeline<*, ApplicationCall>, B : Any, F : Any> P.installIfNotContains(
    plugin: Plugin<P, B, F>,
    configure: B.() -> Unit = {}
) {
    if (!pluginRegistry.contains(plugin.key))
        install(plugin, configure)
}
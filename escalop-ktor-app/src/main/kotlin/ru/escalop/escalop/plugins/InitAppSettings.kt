package ru.escalop.escalop.plugins

import io.ktor.server.application.*
import ru.escalop.cassandra.CassandraClientStub
import ru.escalop.common.EscalopCorSettings
import ru.escalop.escalop.biz.SnapshotProcessor
import ru.escalop.escalop.settings.EscalopAppSettings
import ru.escalop.google.GoogleCalendarStub


fun Application.initAppSettings(): EscalopAppSettings {
    val corSettings = EscalopCorSettings(
        loggerProvider = getLoggerProviderConf(),
        repoTest = getDatabaseConf(EscalopDbType.TEST),
        repoProd = getDatabaseConf(EscalopDbType.PROD),
        repoStub = CassandraClientStub(),
        calendarStub = GoogleCalendarStub()
    )
    return EscalopAppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
        corSettings = corSettings,
        processor = SnapshotProcessor(
            corSettings
        ),
    )
}

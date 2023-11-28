package ru.otus.escalop.plugins

import io.ktor.server.application.*
import ru.otus.cassandra.CassandraClientStub
import ru.otus.common.EscalopCorSettings
import ru.otus.escalop.biz.SnapshotProcessor
import ru.otus.escalop.module
import ru.otus.escalop.settings.EscalopAppSettings
import ru.otus.google.GoogleCalendarStub


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

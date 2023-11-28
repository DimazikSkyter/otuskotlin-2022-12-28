package ru.otus.escalop.settings

import ru.otus.common.EscalopCorSettings
import ru.otus.escalop.biz.SnapshotProcessor

interface AppSettings {
    val processor: SnapshotProcessor
    val corSettings: EscalopCorSettings
}
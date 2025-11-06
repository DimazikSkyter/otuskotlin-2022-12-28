package ru.escalop.escalop.settings

import ru.escalop.common.EscalopCorSettings
import ru.escalop.escalop.biz.SnapshotProcessor

interface AppSettings {
    val processor: SnapshotProcessor
    val corSettings: EscalopCorSettings
}
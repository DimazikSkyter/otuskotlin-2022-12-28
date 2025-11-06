package ru.escalop.escalop.settings

import ru.escalop.common.EscalopCorSettings
import ru.escalop.escalop.biz.SnapshotProcessor

data class EscalopAppSettings(
    val appUrls: List<String> = emptyList(),
    override val processor: SnapshotProcessor,
    override val corSettings: EscalopCorSettings
) : AppSettings
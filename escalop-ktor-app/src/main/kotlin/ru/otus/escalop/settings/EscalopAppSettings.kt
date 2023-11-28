package ru.otus.escalop.settings

import ru.otus.common.EscalopCorSettings
import ru.otus.escalop.biz.SnapshotProcessor

data class EscalopAppSettings(
    val appUrls: List<String> = emptyList(),
    override val processor: SnapshotProcessor,
    override val corSettings: EscalopCorSettings
) : AppSettings
package ru.otus.escalop.plugins

import io.ktor.server.application.*
import ru.otus.common.repo.ISnapshotRepository


//todo доработать при добавлении БД
fun Application.getDatabaseConf(type: EscalopDbType): ISnapshotRepository {
    return ISnapshotRepository.NONE
}

enum class EscalopDbType(val confName: String) {
    PROD("prod"), TEST("test")
}

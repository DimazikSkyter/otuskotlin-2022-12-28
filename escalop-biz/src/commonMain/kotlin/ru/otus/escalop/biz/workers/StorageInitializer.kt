package ru.otus.escalop.biz.workers

import ru.otus.common.EscalopContext
import ru.otus.common.errors.errorAdministration
import ru.otus.common.model.WorkMode
import ru.otus.common.repo.ISnapshotRepository
import ru.otus.escalop.ICorChainDsl
import ru.otus.escalop.worker
import ru.otus.common.errors.fail

fun ICorChainDsl<EscalopContext>.initStorage(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от запрошенного режима работы        
    """.trimIndent()
    handle {

        snapshotRepository = when {
            workMode == WorkMode.TEST -> settings.repoTest
            workMode == WorkMode.STUB -> settings.repoStub
            else -> settings.repoProd
        }
        if (workMode != WorkMode.STUB && snapshotRepository == ISnapshotRepository.NONE) fail(
            errorAdministration(
                field = "repo",
                violationCode = "dbNotConfigured",
                description = "The database is unconfigured for chosen workmode ($workMode). " +
                        "Please, contact the administrator staff"
            )
        )
    }
}
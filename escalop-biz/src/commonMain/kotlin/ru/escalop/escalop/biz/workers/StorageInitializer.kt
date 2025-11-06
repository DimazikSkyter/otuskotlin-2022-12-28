package ru.escalop.escalop.biz.workers

import ru.escalop.common.EscalopContext
import ru.escalop.common.errors.errorAdministration
import ru.escalop.common.model.WorkMode
import ru.escalop.common.repo.ISnapshotRepository
import ru.escalop.escalop.ICorChainDsl
import ru.escalop.escalop.worker
import ru.escalop.common.errors.fail

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
package ru.otus.common

import ru.otus.common.calendar.ICalendarClient
import ru.otus.common.logging.CustomLoggerProvider
import ru.otus.common.model.WorkMode
import ru.otus.common.repo.ISnapshotRepository

data class EscalopCorSettings(
    val loggerProvider: CustomLoggerProvider = CustomLoggerProvider(),
    val repoStub: ISnapshotRepository = ISnapshotRepository.NONE,
    val repoTest: ISnapshotRepository = ISnapshotRepository.NONE,
    val repoProd: ISnapshotRepository = ISnapshotRepository.NONE,
    val calendarStub: ICalendarClient = ICalendarClient.NONE,
    val calendarTest: ICalendarClient = ICalendarClient.NONE,
    val calendarProd: ICalendarClient = ICalendarClient.NONE) {

    fun snapshotRepository(workMode: WorkMode): ISnapshotRepository {
        return when(workMode) {
            WorkMode.PROD -> repoProd
            WorkMode.TEST -> repoTest
            WorkMode.STUB -> repoStub
        }
    }

    fun calendarClient(workMode: WorkMode): ICalendarClient {
        return when(workMode) {
            WorkMode.PROD -> calendarProd
            WorkMode.TEST -> calendarTest
            WorkMode.STUB -> calendarStub
        }
    }

    companion object {
        val NONE = EscalopCorSettings()
    }
}

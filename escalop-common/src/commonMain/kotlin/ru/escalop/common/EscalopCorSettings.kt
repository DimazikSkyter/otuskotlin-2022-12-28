package ru.escalop.common

import ru.escalop.common.calendar.ICalendarClient
import ru.escalop.common.logging.CustomLoggerProvider
import ru.escalop.common.model.WorkMode
import ru.escalop.common.repo.ISnapshotRepository

data class EscalopCorSettings(
    val loggerProvider: CustomLoggerProvider = CustomLoggerProvider(),
    val repoStub: ISnapshotRepository = ISnapshotRepository.NONE,
    val repoTest: ISnapshotRepository = ISnapshotRepository.NONE,
    val repoProd: ISnapshotRepository = ISnapshotRepository.NONE,
    @OptIn(kotlin.uuid.ExperimentalUuidApi::class)
    val calendarStub: ICalendarClient = ICalendarClient.NONE,
    @OptIn(kotlin.uuid.ExperimentalUuidApi::class)
    val calendarTest: ICalendarClient = ICalendarClient.NONE,
    @OptIn(kotlin.uuid.ExperimentalUuidApi::class)
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

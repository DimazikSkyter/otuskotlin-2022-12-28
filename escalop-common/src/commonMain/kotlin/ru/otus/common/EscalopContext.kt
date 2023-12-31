package ru.otus.common

import kotlinx.datetime.Instant
import ru.otus.common.calendar.ICalendarClient
import ru.otus.common.model.*
import ru.otus.common.repo.ISnapshotRepository

data class EscalopContext(
    var command: UserCommand = UserCommand.NONE,
    var state: RequestState = RequestState.NONE,
    var errors: MutableList<OperationError> = mutableListOf(),

    var workMode: WorkMode = WorkMode.PROD,
    var stubCase: Stubs = Stubs.NONE,

    var requestId: RequestId = RequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var userRequest: UserRequest = EmptyUserRequest(),
    var userId: UserId = UserId.NONE,
    var response: Response = EmptyResponse(),

    var snapshotRepository: ISnapshotRepository = ISnapshotRepository.NONE,
    var calendarClient: ICalendarClient = ICalendarClient.NONE,
    var settings: EscalopCorSettings = EscalopCorSettings(),

    var states: List<EscalopState> = mutableListOf()
)

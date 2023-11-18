package ru.otus.common

import kotlinx.datetime.Instant
import ru.otus.common.model.*

data class EsculapContext(
    var command: UserCommand = UserCommand.NONE,
    var state: RequestState = RequestState.NONE,
    var errors: MutableList<OperationError> = mutableListOf(),

    var workMode: WorkMode = WorkMode.PROD,
    var stubCase: Stubs = Stubs.NONE,

    var requestId: RequestId = RequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var userRequest: UserRequest = EmptyUserRequest(),
    var userId: UserId = UserId.NONE,
    var response: Response = EmptyResponse()
)

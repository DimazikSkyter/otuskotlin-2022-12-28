package ru.otus.escalop.biz.workers.stubs

import ru.otus.common.EscalopContext
import ru.otus.common.model.*
import ru.otus.escalop.ICorChainDsl
import ru.otus.escalop.worker


fun ICorChainDsl<EscalopContext>.stubSessionNotFoundException(title: String) = worker {
    this.title = title
    on { stubCase == Stubs.NOT_FOUND && state == RequestState.RUNNING }
    handle {
        state = RequestState.FAILING
        errors.add(OperationError("11", "user errors", "", "Session not found, re-authentication please.", OperationError.Level.ERROR))
        response = DocumentUploadResponse(false, "ERROR")
    }
}

fun ICorChainDsl<EscalopContext>.stubSnapshotNotFound(title: String) = worker {
    this.title = title
    on { stubCase == Stubs.NOT_FOUND && state == RequestState.RUNNING}
    handle {
        state = RequestState.FAILING
        errors.add(OperationError("12", "data errors", "", "Snapshot not found for id ${(this.userRequest as ReadSnapshotRequest).id.get()}"))
        response = SnapshotReadResponse((this.userRequest as ReadSnapshotRequest).id, null)
    }
}


fun ICorChainDsl<EscalopContext>.stubSnapshotNotFoundOnSearch(title: String) = worker {
    this.title = title
    on { stubCase == Stubs.NOT_FOUND && state == RequestState.RUNNING}
    handle {
        state = RequestState.FAILING
        val searchSnapshotRequest = userRequest as SearchSnapshotRequest
        errors.add(OperationError("13", "data errors", "", "No snapshots for ${searchSnapshotRequest.userFilterRequest}"))
        response = SnapshotSearchResponse(listOf())
    }
}

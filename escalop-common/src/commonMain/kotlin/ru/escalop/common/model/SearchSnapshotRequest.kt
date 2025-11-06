package ru.escalop.common.model

data class SearchSnapshotRequest(
    var userFilterRequest: UserFilterRequest
) : UserRequest()
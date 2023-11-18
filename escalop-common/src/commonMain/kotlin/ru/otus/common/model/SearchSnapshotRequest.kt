package ru.otus.common.model

data class SearchSnapshotRequest(
    var userFilterRequest: UserFilterRequest
) : UserRequest()
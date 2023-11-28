package ru.otus.common.repo

interface ISnapshotRepository {
    suspend fun createSnapshot(rq: DbSnapshotCreateRequest): DbSnapshotInfoResponse

    suspend fun updateSnapshot(rq: DbSnapshotCreateRequest): DbSnapshotInfoResponse
    suspend fun readSnapshot(rq: DbSnapshotIdRequest): DbSnapshotResponse
    suspend fun searchSearchSnapshot(rq: DbSnapshotFilterRequest): DbSnapshotsResponse
    companion object {
        val NONE = object : ISnapshotRepository {
            override suspend fun createSnapshot(rq: DbSnapshotCreateRequest): DbSnapshotInfoResponse {
                TODO("Not yet implemented")
            }

            override suspend fun updateSnapshot(rq: DbSnapshotCreateRequest): DbSnapshotInfoResponse {
                TODO("Not yet implemented")
            }

            override suspend fun readSnapshot(rq: DbSnapshotIdRequest): DbSnapshotResponse {
                TODO("Not yet implemented")
            }

            override suspend fun searchSearchSnapshot(rq: DbSnapshotFilterRequest): DbSnapshotsResponse {
                TODO("Not yet implemented")
            }
        }
    }
}

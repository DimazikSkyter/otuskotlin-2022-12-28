package ru.escalop.common.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual

@Entity
@Table(name = "snapshot_states")
class SnapshotStatesEntity (
    @Id
    val id: Long,
    val snapshotId: Long,
    val timestamp: LocalDateTime,
    val state: String
)
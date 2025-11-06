package ru.escalop.common.entity

import jakarta.persistence.*
import ru.escalop.common.converters.ListToStringJsonConverter
import ru.escalop.common.model.DocumentType
import java.time.LocalDate

@Entity
@Table(name = "snapshots")
class Snapshot(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false)
    val type: DocumentType,

    val index: Long,
    val date: LocalDate,

    @Convert(converter = ListToStringJsonConverter::class)
    @Column(columnDefinition = "jsonb")
    val metrics: List<String>,

    val sourceDocumentName: String,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @OneToMany(mappedBy = "snapshot")
    val states: MutableList<SnapshotStatesEntity> = mutableListOf()
)
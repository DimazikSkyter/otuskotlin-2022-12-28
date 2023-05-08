package ru.otus.homework2.entity

import jakarta.persistence.*


@Entity
@Table(name = "medicine")
data class MedicineUnit (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String?,
    var description: String?,
    var linkToRlsNet: String?
        ) {

    constructor() : this(null, null, null, null) {

    }
}

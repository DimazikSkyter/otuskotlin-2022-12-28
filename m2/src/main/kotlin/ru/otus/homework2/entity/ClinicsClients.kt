package ru.otus.homework2.entity

import jakarta.persistence.*
import ru.otus.homework2.model.ClinicClientStatus

@Entity
@Table(name = "clinics_clients")
class ClinicsClients (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var clientId: Long?,
    var clinicId: Long?,
    var status: ClinicClientStatus?
        ) {
    constructor() : this(null, null, null, null) {

    }
}
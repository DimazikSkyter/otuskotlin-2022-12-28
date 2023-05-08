package ru.otus.homework2.entity

import jakarta.persistence.*


@Entity
@Table(name = "treatments")
class Treatment (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var header: String?,
    var description: String?,
    var thread: Int?,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "treatments_clients",
        joinColumns = [JoinColumn(name = "treatment_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "client_id", referencedColumnName = "id")],
    )
    var client: Client?,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "treatments_clinics",
        joinColumns = [JoinColumn(name = "treatment_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "clinic_id", referencedColumnName = "id")],
    )
    var clinic: Clinic?,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "treatments_doctors",
        joinColumns = [JoinColumn(name = "treatment_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "doctor_id", referencedColumnName = "id")],
    )
    var doctor: Doctor?,
        ) {
    constructor() : this(null, null, null, null, null, null, null) {

    }
}
package ru.otus.homework2.entity

import jakarta.persistence.*


@Entity
@Table(name = "clinics")
class Clinic(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)
package ru.otus.homework2.entity

import jakarta.persistence.*


@Entity
@Table(name = "doctors")
class Doctor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)
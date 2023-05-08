package ru.otus.homework2.entity

import jakarta.persistence.*
import ru.otus.homework2.model.ClientDto
import java.time.LocalDate

@Entity
@Table(name = "clients")
data class Client(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String?,
    var secondName: String?,
    var lastName: String?,
    var sexMale: Boolean,
    var birthDate: LocalDate?,
) {


    constructor() : this(null, null, null, null, false, null)

    fun transformToDto() : ClientDto {
        return ClientDto("$lastName $name $secondName", birthDate, sexMale)
    }

}
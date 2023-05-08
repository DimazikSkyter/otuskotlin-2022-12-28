package ru.otus.homework2.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.otus.homework2.entity.ClinicsClients

interface ClinicsClientsRepository : JpaRepository<ClinicsClients, Long> {

    fun existsByClientIdAndClinicId(clientId: Long, clinicId: Long) : Boolean
}
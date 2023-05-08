package ru.otus.homework2.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.otus.homework2.entity.Treatment

interface TreatmentRepository : JpaRepository<Treatment, Long> {

    fun findByThread(thread: Int): List<Treatment>
}
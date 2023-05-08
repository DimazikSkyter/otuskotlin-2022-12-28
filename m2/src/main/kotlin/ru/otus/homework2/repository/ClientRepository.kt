package ru.otus.homework2.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.otus.homework2.entity.Client

interface ClientRepository : JpaRepository <Client, Long> {
}
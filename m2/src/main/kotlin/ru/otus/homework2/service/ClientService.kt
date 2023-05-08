package ru.otus.homework2.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.otus.homework2.entity.Client
import ru.otus.homework2.model.ClientDto
import ru.otus.homework2.repository.ClientRepository
import ru.otus.homework2.responsemodel.ClientStatus
import java.lang.Exception

interface ClientService {

    fun newClient(client: ClientDto): ClientStatus

    fun updateClient(clientId : Long, client: ClientDto): ClientStatus

    fun getClient(id: Long): ClientDto?
}


@Service
class ClientServiceImpl(
    var clientRepository: ClientRepository
) : ClientService {

    val logger = LoggerFactory.getLogger(ClientServiceImpl::class.java.name)!!

    override fun newClient(client: ClientDto): ClientStatus {
        return try {

            val savedClient = client.let {
                val fio = splitFio(it.fio!!)
                clientRepository.saveAndFlush(
                    Client(
                        null,
                        fio[0],
                        fio[1],
                        fio[2],
                        it.sexMale!!,
                        it.birthDate
                    )
                )
            }
            ClientStatus.success(savedClient.id!!)
        } catch (e: Exception) {
            logger.error("Failed to create new client", e)
            ClientStatus.failed("Failed to create new client")
        }
    }

    override fun updateClient(clientId : Long, clientDto: ClientDto): ClientStatus {
        return try {
            val client = clientRepository.getReferenceById(clientId)
            fillNonNull(client, clientDto)
            clientRepository.save(client)
            ClientStatus.success(clientId)
        } catch (e: Exception) {
            ClientStatus.failed("Failed to update client.")
        }
    }

    override fun getClient(id: Long): ClientDto? = try {
        clientRepository.getReferenceById(id).transformToDto()
    } catch (e: Exception) {
        logger.error("Failed to get client by id {}", id)
        null
    }

    private fun fillNonNull(client: Client, clientDto: ClientDto) {
        if (clientDto.sexMale != null) {
            client.sexMale = clientDto.sexMale
        }
        if (clientDto.fio != null) {
            val splitFio = splitFio(clientDto.fio)

            client.name = splitFio[0]
            client.secondName = splitFio[1]
            client.lastName = splitFio[2]
        }
        if (clientDto.birthDate != null) {
            client.birthDate = clientDto.birthDate
        }
    }

    private fun splitFio(fio: String): List<String> {
        val splitFio = fio.split(" ")
        if (splitFio.size == 2) {
            val mutableFio = splitFio.toMutableList()
            mutableFio.add("")
            return mutableFio
        } else if (splitFio.size != 3) {
            throw IndexOutOfBoundsException("Fio can have 2 or 3 parts")
        }
        return splitFio
    }

}
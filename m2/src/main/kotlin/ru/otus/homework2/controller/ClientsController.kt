package ru.otus.homework2.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import ru.otus.homework2.model.ClientDto
import ru.otus.homework2.responsemodel.ClientStatus
import ru.otus.homework2.service.ClientService

@RestController
@RequestMapping("/clients")
class ClientsController(
    val clientService: ClientService
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @PostMapping
    @ResponseBody
    fun createNewClient(@RequestBody client: ClientDto, @RequestHeader clinicId: Long?): ClientStatus {
        clinicId?.let { logger.info("Trying to register new client from clinic {}.", clinicId) }
            ?: run { logger.info("Trying to register new client from mobile app.") }

        logger.debug("New client info: {}.", client)
        return clientService.newClient(client)
    }

    @PutMapping("/{id}")
    @ResponseBody
    fun updateClient(@PathVariable("id") id : Long, @RequestBody client: ClientDto, @RequestHeader clinicId: Long?): ClientStatus {
        clinicId?.let { logger.info("Trying to register new client from clinic {}.", clinicId) }
            ?: run { logger.info("Trying to register new client from mobile app.") }

        logger.debug("Update client info: {}.", client)
        return clientService.updateClient(id, client)
    }

    @GetMapping("/{id}")
    @ResponseBody
    fun getClient(@PathVariable("id") id : Long, @RequestHeader clinicId: Long?): ClientDto? {
        clinicId?.let { logger.info("Trying to register new client from clinic {}.", clinicId) }
            ?: run { logger.info("Trying to register new client from mobile app.") }

        logger.debug("Get client {} info.", id)
        return clientService.getClient(id)
    }
}
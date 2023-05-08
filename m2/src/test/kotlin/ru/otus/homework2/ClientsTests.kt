package ru.otus.homework2

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content

import ru.otus.homework2.controller.ClientsController
import ru.otus.homework2.model.ClientDto
import ru.otus.homework2.repository.ClientRepository
import ru.otus.homework2.responsemodel.ClientStatus
import ru.otus.homework2.service.ClientService
import ru.otus.homework2.service.ClientServiceImpl
import java.time.LocalDate

@WebMvcTest(ClientsController::class, ClientServiceImpl::class)
@AutoConfigureMockMvc
class ClientsTests {

    @MockBean
    private lateinit var clientService: ClientService

    @MockBean
    private lateinit var clientRepository: ClientRepository

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `should create new client`() {
        val clientId: Long = 65
        val client = ClientDto("Иванов Петр Сергеевич", LocalDate.of(1999, 3, 5), true)

        Mockito.doReturn(ClientStatus.success(clientId)).`when`(clientService).newClient(client)

        mockMvc.perform(post("/clients").contentType(MediaType.APPLICATION_JSON).content(client.toJson().toByteArray()))
            .andDo(print()).andExpect(content().json("{\"clientId\":$clientId,\"errorMessage\":null}"))
    }

    @Test
    fun `should change client`() {
        val clientId: Long = 33
        val client = ClientDto("Александров Иван Иванович", LocalDate.of(2001, 3, 5), true)

        Mockito.doReturn(ClientStatus.success(clientId)).`when`(clientService).updateClient(clientId, client)

        mockMvc.perform(put("/clients/$clientId").contentType(MediaType.APPLICATION_JSON).content(client.toJson().toByteArray()))
            .andDo(print()).andExpect(content().json("{\"clientId\":$clientId,\"errorMessage\":null}"))
    }

    @Test
    fun `should get client info`() {
        val clientId: Long = 76
        val client = ClientDto("Фетин Михаил Александрович", LocalDate.of(1976, 3, 5), true)

        Mockito.doReturn(client).`when`(clientService).getClient(clientId)

        mockMvc.perform(get("/clients/$clientId").contentType(MediaType.APPLICATION_JSON).content(client.toJson().toByteArray()))
            .andDo(print()).andExpect(content().json(client.toJson()))
    }
}
package ru.otus.homework2.mvc

import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import ru.otus.homework2.controller.ClinicController
import ru.otus.homework2.model.TreatmentDto
import ru.otus.homework2.repository.ClientRepository
import ru.otus.homework2.repository.ClinicRepository
import ru.otus.homework2.repository.ClinicsClientsRepository
import ru.otus.homework2.repository.TreatmentRepository
import ru.otus.homework2.responsemodel.ClinicStatus
import ru.otus.homework2.service.ClinicService


@WebMvcTest(ClinicController::class, ClinicService::class)
@AutoConfigureMockMvc
class ClinicsTests {


    @MockBean
    private lateinit var clinicService: ClinicService

    @MockBean
    private lateinit var treatmentRepository: TreatmentRepository
    @MockBean
    private lateinit var clinicsClientsRepository: ClinicsClientsRepository
    @MockBean
    private lateinit var clientRepository: ClientRepository
    @MockBean
    private lateinit var clinicsRepository: ClinicRepository


    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `should clinic create new treatment`() {
        val treatmentId: Long = 5551
        val clientId: Long = 65
        val clinicId: Long = 821
        val treatmentDto: TreatmentDto = TreatmentDto(
            "header",
            "description",
            33,
            131
        )
        val clinicStatus = ClinicStatus.clinicNewTreatment(treatmentId)

        Mockito.doReturn(clinicStatus).`when`(clinicService).newTreatment(clientId, clinicId, treatmentDto)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/clinics/treatment/new").contentType(MediaType.APPLICATION_JSON)
                .param("clientId", clientId.toString())
                .header("clinicId", clinicId)
                .content(treatmentDto.toJson().toByteArray()))
            .andDo(MockMvcResultHandlers.print()).andExpect(jsonPath("$.treatmentId", `is`(treatmentId.toInt())))
    }


    @Test
    fun `should clinic get treatment`() {
        val doctorId = 1054L
        val thread = 31
        val treatmentId: Long = 13
        val clinicId: Long = 335

        val treatmentDto: TreatmentDto = TreatmentDto("header1", "body 10", thread, doctorId)
        Mockito.doReturn(treatmentDto).`when`(clinicService).getTreatmentById(treatmentId)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/clinics/treatment/$treatmentId").contentType(MediaType.APPLICATION_JSON)
                .header("clinicId", clinicId))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(jsonPath("$.doctorId", `is`(doctorId.toInt())))
            .andExpect(jsonPath("$.thread", `is`(thread)))
    }


    @Test
    fun `should clinic get treatment branch`() {
        val doctorId1 = 1054L
        val doctorId2 = 1056L
        val thread = 313
        val clinicId: Long = 335

        val treatmentDto1: TreatmentDto = TreatmentDto("header1", "body 10", thread, doctorId1)
        val treatmentDto2: TreatmentDto = TreatmentDto("header2", "body 22", thread, doctorId1)
        val treatmentDto3: TreatmentDto = TreatmentDto("header3", "bod y33", thread, doctorId2)
        Mockito.doReturn(listOf(treatmentDto1, treatmentDto2, treatmentDto3)).`when`(clinicService).getTreatmentByThread(thread)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/clinics/treatment/thread/$thread").contentType(MediaType.APPLICATION_JSON)
                .header("clinicId", clinicId))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(jsonPath("$.length()").value(3))
    }
}
package ru.otus.homework2.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.otus.homework2.entity.*
import ru.otus.homework2.model.ClinicClientStatus
import ru.otus.homework2.model.TreatmentDto
import ru.otus.homework2.repository.*
import ru.otus.homework2.responsemodel.ClinicStatus
import java.lang.Exception
import kotlin.streams.toList

interface ClinicService {

    fun linkClientToClinic(clientId: Long, clinicId: Long): ClinicStatus

    fun newTreatment(clientId: Long, clinicId: Long, treatmentDto: TreatmentDto): ClinicStatus

    fun getTreatmentById(id: Long): TreatmentDto

    fun getTreatmentByThread(id: Int): List<TreatmentDto>
}

@Service
class ClinicServiceImpl(
    var clinicsClientsRepository: ClinicsClientsRepository,
    var clientRepository: ClientRepository,
    var clinicsRepository: ClinicRepository,
    var treatmentRepository: TreatmentRepository,
    var doctorRepository: DoctorRepository
) : ClinicService {

    val logger = LoggerFactory.getLogger(ClientServiceImpl::class.java.name)!!

    override fun linkClientToClinic(clientId: Long, clinicId: Long): ClinicStatus {
        val clientStatus: ClinicStatus = try {
            //todo move validation to another service and controller
            if (!clientRepository.existsById(clientId)) {
                ClinicStatus.clinicStatusError("Client $clientId doesn't found.")
            } else if (clinicsClientsRepository.existsByClientIdAndClinicId(clientId, clinicId)) {
                logger.warn(
                    "Link between client {} and clinic {}  already exists, operation aborted.",
                    clientId,
                    clinicId
                )
                ClinicStatus.clinicStatusOk("Link with client $clientId already enabled.")
            } else {
                clinicsClientsRepository.save(
                    ClinicsClients(
                        null,
                        clientId,
                        clinicId,
                        ClinicClientStatus.Served
                    )
                )
                ClinicStatus("Link with client $clientId enabled", null, null)
            }
        } catch (e: Exception) {
            logger.error("Catch exception while create new link for clientId {} and clinicId {}", clientId, clinicId)
            ClinicStatus.clinicStatusError("Failed to link clinic $clinicId with client $clientId. Please, contact support.")
        }
        return clientStatus
    }

    override fun newTreatment(clientId: Long, clinicId: Long, treatmentDto: TreatmentDto): ClinicStatus {
        val clinicStatus = try {
            val treatment = treatmentRepository.saveAndFlush(
                Treatment(
                    null,
                    treatmentDto.header,
                    treatmentDto.description,
                    treatmentDto.thread,
                    Client(clientId, null, null, null, false, null),
                    Clinic(clinicId),
                    Doctor(treatmentDto.doctorId)
                )
            )
            ClinicStatus.clinicNewTreatment(treatment.id!!)
        } catch (e: Exception) {
            logger.error(
                "Catch exception while create new treatment with parameters clientId {}, clinicId {}, treatmentDto {}",
                clientId,
                clinicId,
                treatmentDto,
                e
            )
            ClinicStatus.clinicStatusError("Failed to create new treatment. Please, contact support.")
        }
        return clinicStatus
    }

    override fun getTreatmentById(id: Long): TreatmentDto = treatmentRepository.findById(id).map { treatment ->
        TreatmentDto(treatment.header!!, treatment.description!!, treatment.thread!!, treatment.doctor!!.id!!)
    }
        .get()

    override fun getTreatmentByThread(threadId: Int): List<TreatmentDto> = treatmentRepository
        .findByThread(threadId.toInt())
        .stream()
        .map { treatment ->
            TreatmentDto(treatment.header!!, treatment.description!!, treatment.thread!!, treatment.doctor!!.id!!)
        }
        .toList()
}

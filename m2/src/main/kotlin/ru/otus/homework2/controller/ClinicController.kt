package ru.otus.homework2.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.otus.homework2.model.TreatmentDto
import ru.otus.homework2.responsemodel.ClinicStatus
import ru.otus.homework2.service.ClinicService


@RestController
@RequestMapping("/clinics")
class ClinicController (
    var clinicService: ClinicService
        ) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @PatchMapping("/links")
    fun linkClientToClinic(@RequestParam("clientId") clientId : Long, @RequestParam operation : String, @RequestHeader clinicId: Long): ClinicStatus {
        logger.info("Get new link request with clientId {}, clinicId {} and operation {}", clientId, clinicId, operation)
        if (operation == "link")
            return clinicService.linkClientToClinic(clientId, clinicId)
        else if (operation == "removed")
            return clinicService.linkClientToClinic(clientId, clinicId)
        return ClinicStatus.clinicStatusError("Unknown operation $operation.")
    }

    @PostMapping("/treatment/new")
    fun newTreatmentBranch(@RequestParam("clientId") clientId: Long,
                           @RequestHeader clinicId: Long,
                           @RequestBody treatmentDto: TreatmentDto): ClinicStatus = clinicService.newTreatment(clientId, clinicId, treatmentDto)

    @GetMapping("/treatment/{id}")
    fun getTreatmentById(@PathVariable("id") id : Long,
                         @RequestHeader clinicId: Long): TreatmentDto = clinicService.getTreatmentById(id)

    @GetMapping("/treatment/thread/{thread}")
    fun getTreatmentsByBranch(@PathVariable("thread") thread : Int,
                              @RequestHeader clinicId: Long): List<TreatmentDto>  = clinicService.getTreatmentByThread(thread)

    @ExceptionHandler(Exception::class)
    fun handle(
        ex: Exception?,
        request: HttpServletRequest?, response: HttpServletResponse?
    ): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something wrong. Please, try it later.")
    }
}
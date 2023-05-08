package ru.otus.homework2.responsemodel

data class ClinicStatus (
    var message: String?,
    var errorMessage: String?,
    var treatmentId: Long?
        ) {


    companion object {

        fun clinicNewTreatment(treatmentId: Long): ClinicStatus {
            return ClinicStatus("Treatment created", null, treatmentId)
        }

        fun clinicStatusOk(message: String?): ClinicStatus {
            return ClinicStatus(message, null, null)
        }

        fun clinicStatusError(errorMessage: String?): ClinicStatus {
            return ClinicStatus(null, errorMessage, null)
        }
    }
}
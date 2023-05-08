package ru.otus.homework2.responsemodel

data class AdminStatus (
    var status : Status,
    var message : String?,
    var errorMessage : String?,
        ) {

    enum class Status (
        var result : String
            ) {
        OK("successful"), ERROR("Get error");
    }
     companion object {

         fun ok(message : String) : AdminStatus {
             return AdminStatus(Status.OK, message, null)
         }

         fun error(errorMessage: String) : AdminStatus {
             return AdminStatus(Status.ERROR, null, errorMessage)
         }
     }
}
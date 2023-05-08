package ru.otus.homework2.responsemodel

data class ClientStatus(
    var clientId: Long?,
    var errorMessage: String?
) {
  companion object {

      fun success(clientId: Long) :ClientStatus = ClientStatus(clientId, null)

      fun failed(errorMessage: String?) :ClientStatus = ClientStatus(null, errorMessage)

      fun failed(clientId: Long, errorMessage: String?) :ClientStatus = ClientStatus(clientId, errorMessage)
  }
}
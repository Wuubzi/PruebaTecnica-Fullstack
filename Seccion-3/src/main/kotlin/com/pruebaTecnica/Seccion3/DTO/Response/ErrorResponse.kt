package com.pruebaTecnica.Seccion3.DTO.Response

data class ErrorResponse(
    val message: String,
    val status: Int,
    val errors: Map<String, String> = emptyMap()
)

package com.pruebaTecnica.Seccion3.DTO.Request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.util.UUID

import jakarta.validation.constraints.NotEmpty

data class CreateOrderRequest(
    @field:NotEmpty
    val items: List <OrderItemRequest>
)

data class OrderItemRequest(
    @field:NotNull
    val productId: UUID,

    @field:Min(1)
    val quantity: Int,
)

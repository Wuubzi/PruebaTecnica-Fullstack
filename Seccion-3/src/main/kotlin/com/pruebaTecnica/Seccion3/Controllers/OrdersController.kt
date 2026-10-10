package com.pruebaTecnica.Seccion3.Controllers

import com.pruebaTecnica.Seccion3.DTO.Request.CreateOrderRequest
import com.pruebaTecnica.Seccion3.DTO.Response.OrderResponse
import com.pruebaTecnica.Seccion3.Services.OrdersService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID


@RestController
@RequestMapping("/api/v1/orders")
class OrdersController(
    private val ordersService: OrdersService
) {

    @PostMapping("/{userId}")
    fun createOrder(
        @PathVariable userId: UUID,
        @RequestHeader("Idempotency-Key") idempotencyKey: UUID,
        @Valid @RequestBody createOrderRequest: CreateOrderRequest
    ): ResponseEntity<OrderResponse> {
        return ResponseEntity.ok(ordersService.createOrder(userId, idempotencyKey, createOrderRequest))
    }

}
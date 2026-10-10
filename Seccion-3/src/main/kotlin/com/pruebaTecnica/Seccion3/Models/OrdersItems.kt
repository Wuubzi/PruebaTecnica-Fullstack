package com.pruebaTecnica.Seccion3.Models

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.UUID

@Entity
@Table(name = "orders_items")
class OrdersItems(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.UUID)
    var idOrderItem: UUID? = null,
    @field:ManyToOne(fetch = FetchType.LAZY, optional = false)
    @field:JoinColumn(name = "id_order", nullable = false)
    var order: Orders? = null,
    @field:ManyToOne(fetch = FetchType.LAZY, optional = false)
    @field:JoinColumn(name = "id_product", nullable = false)
    var product: Products? = null,
    var unitPrice: BigDecimal = BigDecimal.ZERO,
    var quantity: Int = 0
)

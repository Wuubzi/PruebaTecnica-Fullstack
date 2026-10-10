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
@Table(name = "orders")
class Orders(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.UUID)
    var idOrder: UUID? = null,
    var status: String = "",
    var total: BigDecimal = BigDecimal.ZERO,
    @field:ManyToOne(fetch = FetchType.LAZY, optional = false)
    @field:JoinColumn(name = "id_user", nullable = false)
    var user: Users? = null
)

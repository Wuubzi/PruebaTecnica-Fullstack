package com.pruebaTecnica.Seccion3.Models

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.MapsId
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "inventory")
class Inventory(
    @field:Id
    var idProduct: UUID? = null,
    var sku: String = "",
    var quantityOnHand: Int = 0,
    var quantityReserved: Int = 0,
    @field:MapsId
    @field:OneToOne(fetch = FetchType.LAZY, optional = false)
    @field:JoinColumn(name = "id_product", nullable = false)
    var product: Products? = null
)

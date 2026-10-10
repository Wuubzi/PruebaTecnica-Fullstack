package com.pruebaTecnica.Seccion3.Models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.UUID

@Entity
@Table(name = "products")
class Products(
    @field:Id
    var idProduct: UUID? = null,
    var name: String = "",
    var description: String = "",
    var image: String = "",
    var price: BigDecimal = BigDecimal.ZERO
)

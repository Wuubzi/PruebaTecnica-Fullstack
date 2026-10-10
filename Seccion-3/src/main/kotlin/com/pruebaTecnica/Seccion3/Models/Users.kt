package com.pruebaTecnica.Seccion3.Models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "users")
class Users(
    @field:Id
    var idUser: UUID? = null,
    var name: String = "",
    var email: String = "",
    var password: String = ""
)

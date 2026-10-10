package com.pruebaTecnica.Seccion3.Repositories

import com.pruebaTecnica.Seccion3.Models.Orders
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface OrdersRepository: JpaRepository<Orders, UUID> {
}
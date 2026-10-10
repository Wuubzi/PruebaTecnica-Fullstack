package com.pruebaTecnica.Seccion3.Services

import com.pruebaTecnica.Seccion3.DTO.Request.CreateOrderRequest
import com.pruebaTecnica.Seccion3.DTO.Response.OrderResponse
import com.pruebaTecnica.Seccion3.Exceptions.ConflictException
import com.pruebaTecnica.Seccion3.Exceptions.InsufficientStockException
import com.pruebaTecnica.Seccion3.Exceptions.ResourceNotFoundException
import com.pruebaTecnica.Seccion3.Models.IdempotencyKey
import com.pruebaTecnica.Seccion3.Models.Orders
import com.pruebaTecnica.Seccion3.Models.OrdersItems
import com.pruebaTecnica.Seccion3.Models.Users
import com.pruebaTecnica.Seccion3.Repositories.IdempotencyKeyRepository
import com.pruebaTecnica.Seccion3.Repositories.InventoryRepository
import com.pruebaTecnica.Seccion3.Repositories.OrdersItemsRepository
import com.pruebaTecnica.Seccion3.Repositories.OrdersRepository
import com.pruebaTecnica.Seccion3.Repositories.ProductsRepository
import com.pruebaTecnica.Seccion3.Repositories.UsersRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.UUID

@Service
class OrdersService(
    private val ordersRepository: OrdersRepository,
    private val productsRepository: ProductsRepository,
    private val inventoryRepository: InventoryRepository,
    private val ordersItemsRepository: OrdersItemsRepository,
    private val idempotencyKeyRepository: IdempotencyKeyRepository,
    private val usersRepository: UsersRepository
) {

    @Transactional
    fun createOrder(userId: UUID, key: UUID, createOrderRequest: CreateOrderRequest): OrderResponse {
        if (idempotencyKeyRepository.existsById(key)) {
            throw ConflictException("Idempotency key already exists")
        }

        val user = usersRepository.findById(userId)
            .orElseThrow { ResourceNotFoundException("User not found: $userId") }

        val order = ordersRepository.save(
            Orders(
                status = "CREATED",
                total = BigDecimal.ZERO,
                user = user
            )
        )

        val orderItems = createOrderRequest.items.map { item ->
            val product = productsRepository.findById(item.productId)
                .orElseThrow {
                    ResourceNotFoundException("Product not found: ${item.productId}")
                }

            val inventory = inventoryRepository.findById(item.productId)
                .orElseThrow {
                    ResourceNotFoundException("Inventory not found for product: ${item.productId}")
                }

            val availableStock = inventory.quantityOnHand - inventory.quantityReserved
            if (item.quantity > availableStock) {
                throw InsufficientStockException(
                    "Insufficient stock for product: ${item.productId}"
                )
            }

            order.total = order.total.add(
                product.price.multiply(BigDecimal.valueOf(item.quantity.toLong()))
            )

            OrdersItems(
                product = product,
                quantity = item.quantity,
                unitPrice = product.price,
                order = order
            )
        }

        ordersItemsRepository.saveAll(orderItems)
        ordersRepository.save(order)

        idempotencyKeyRepository.save(
            IdempotencyKey(
                idKey = key,
                requestHash = createOrderRequest.hashCode().toString(),
                status = "COMPLETED",
                user = user,
                order = order
            )
        )

        return OrderResponse("Order created successfully", 200)
    }
}
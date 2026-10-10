package com.pruebaTecnica.Seccion3.Models

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.PostLoad
import jakarta.persistence.PostPersist
import jakarta.persistence.Table
import jakarta.persistence.Transient
import org.springframework.data.domain.Persistable
import java.util.UUID

@Entity
@Table(name = "idempotency_key")
class IdempotencyKey(
    @field:Id
    var idKey: UUID? = null,
    var requestHash: String = "",
    var status: String = "",
    @field:ManyToOne(fetch = FetchType.LAZY, optional = false)
    @field:JoinColumn(name = "id_user", nullable = false)
    var user: Users? = null,
    @field:OneToOne(fetch = FetchType.LAZY, optional = false)
    @field:JoinColumn(name = "id_order", nullable = false)
    var order: Orders? = null
): Persistable<UUID> {

    @field:Transient
    private var newEntity = true

    override fun getId(): UUID? = idKey

    override fun isNew(): Boolean = newEntity

    @PostPersist
    @PostLoad
    private fun markAsNotNew() {
        newEntity = false
    }
}

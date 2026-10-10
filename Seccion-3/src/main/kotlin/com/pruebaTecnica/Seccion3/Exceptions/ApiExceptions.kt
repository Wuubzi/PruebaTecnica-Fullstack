package com.pruebaTecnica.Seccion3.Exceptions

class ResourceNotFoundException(message: String) : RuntimeException(message)

class InsufficientStockException(message: String) : RuntimeException(message)

class ConflictException(message: String) : RuntimeException(message)

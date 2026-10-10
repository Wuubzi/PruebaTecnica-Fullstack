package com.pruebaTecnica.Seccion3.GlobalExceptionHandler

import com.pruebaTecnica.Seccion3.DTO.Response.ErrorResponse
import com.pruebaTecnica.Seccion3.Exceptions.ConflictException
import com.pruebaTecnica.Seccion3.Exceptions.InsufficientStockException
import com.pruebaTecnica.Seccion3.Exceptions.ResourceNotFoundException
import jakarta.validation.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(exception: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errors = exception.bindingResult.fieldErrors.associate {
            it.field to (it.defaultMessage ?: "Invalid value")
        }

        return errorResponse(HttpStatus.BAD_REQUEST, "Validation failed", errors)
    }

    @ExceptionHandler(
        HttpMessageNotReadableException::class,
        MethodArgumentTypeMismatchException::class,
        ConstraintViolationException::class
    )
    fun handleBadRequest(exception: Exception): ResponseEntity<ErrorResponse> {
        return errorResponse(HttpStatus.BAD_REQUEST, exception.message ?: "Invalid request")
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleNotFound(exception: ResourceNotFoundException): ResponseEntity<ErrorResponse> {
        return errorResponse(HttpStatus.NOT_FOUND, exception.message ?: "Resource not found")
    }

    @ExceptionHandler(InsufficientStockException::class)
    fun handleInsufficientStock(
        exception: InsufficientStockException
    ): ResponseEntity<ErrorResponse> {
        return errorResponse(HttpStatus.CONFLICT, exception.message ?: "Insufficient stock")
    }

    @ExceptionHandler(ConflictException::class, DataIntegrityViolationException::class)
    fun handleConflict(exception: Exception): ResponseEntity<ErrorResponse> {
        return errorResponse(HttpStatus.CONFLICT, exception.message ?: "Conflict")
    }

    @ExceptionHandler(Exception::class)
    fun handleInternalError(exception: Exception): ResponseEntity<ErrorResponse> {
        return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error")
    }

    private fun errorResponse(
        status: HttpStatus,
        message: String,
        errors: Map<String, String> = emptyMap()
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(status)
            .body(ErrorResponse(message, status.value(), errors))
    }
}
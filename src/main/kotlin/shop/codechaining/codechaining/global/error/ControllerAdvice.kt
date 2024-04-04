package shop.codechaining.codechaining.global.error

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import shop.codechaining.codechaining.global.error.dto.ErrorResponse
import shop.codechaining.codechaining.global.error.exception.AccessDeniedGroupException
import shop.codechaining.codechaining.global.error.exception.AuthGroupException
import shop.codechaining.codechaining.global.error.exception.InvalidGroupException
import shop.codechaining.codechaining.global.error.exception.NotFoundGroupException
import shop.codechaining.codechaining.global.template.RspTemplate

@RestControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(NotFoundGroupException::class)
    fun handleNotFoundDate(e: RuntimeException): RspTemplate<ErrorResponse> {
        val errorResponse = ErrorResponse(HttpStatus.NOT_FOUND.value(), e.message.toString())

        return RspTemplate(statusCode = HttpStatus.NOT_FOUND, data = errorResponse)
    }

    @ExceptionHandler(AuthGroupException::class)
    fun handleAuthDate(e: RuntimeException): RspTemplate<ErrorResponse> {
        val errorResponse = ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.message.toString())

        return RspTemplate(statusCode = HttpStatus.BAD_REQUEST, data = errorResponse)
    }

    @ExceptionHandler(AccessDeniedGroupException::class)
    fun handleAccessDeniedDate(e: RuntimeException): RspTemplate<ErrorResponse> {
        val errorResponse = ErrorResponse(HttpStatus.FORBIDDEN.value(), e.message.toString())

        return RspTemplate(statusCode = HttpStatus.FORBIDDEN, data = errorResponse)
    }

    @ExceptionHandler(InvalidGroupException::class)
    fun handleInvalidDate(e: RuntimeException): RspTemplate<ErrorResponse> {
        val errorResponse = ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.message.toString())

        return RspTemplate(statusCode = HttpStatus.BAD_REQUEST, data = errorResponse)
    }
}
package shop.codechaining.codechaining.global.error

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import shop.codechaining.codechaining.global.error.dto.ErrorResponse
import shop.codechaining.codechaining.global.error.exception.NotFoundGroupException
import shop.codechaining.codechaining.global.template.RspTemplate

@RestControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(NotFoundGroupException::class)
    fun handleNotFoundDate(e: RuntimeException): RspTemplate<ErrorResponse> {
        val errorResponse = ErrorResponse(HttpStatus.NOT_FOUND.value(), e.message.toString())

        return RspTemplate(HttpStatus.NOT_FOUND, "NotFoundError", errorResponse)
    }
}
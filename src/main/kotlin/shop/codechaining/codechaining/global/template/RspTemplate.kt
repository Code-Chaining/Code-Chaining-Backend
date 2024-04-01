package shop.codechaining.codechaining.global.template

import org.springframework.http.HttpStatus

data class RspTemplate<T>(
    var statusCode: HttpStatus,
    var message: String,
    var data: T? = null
)
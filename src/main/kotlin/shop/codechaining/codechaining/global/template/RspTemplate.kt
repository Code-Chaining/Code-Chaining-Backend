package shop.codechaining.codechaining.global.template

import org.springframework.http.HttpStatus

data class RspTemplate<T>(
    var statusCode: HttpStatus,
    var message: String? = null,
    var data: T? = null
)
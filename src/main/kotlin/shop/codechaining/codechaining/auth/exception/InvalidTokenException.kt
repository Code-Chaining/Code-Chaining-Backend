package shop.codechaining.codechaining.auth.exception

import shop.codechaining.codechaining.global.error.exception.InvalidGroupException

class InvalidTokenException : InvalidGroupException {
    constructor(message: String) : super(message)
    constructor() : this("토큰이 유효하지 않습니다.")
}
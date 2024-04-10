package shop.codechaining.codechaining.room.exception

import shop.codechaining.codechaining.global.error.exception.InvalidGroupException

class AlreadyScrappedException : InvalidGroupException {
    constructor(message: String) : super(message)

    constructor() : this("이미 스크랩한 토론 방입니다.")
}
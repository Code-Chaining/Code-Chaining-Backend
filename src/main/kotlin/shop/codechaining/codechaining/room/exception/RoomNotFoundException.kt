package shop.codechaining.codechaining.room.exception

import shop.codechaining.codechaining.global.error.exception.NotFoundGroupException

class RoomNotFoundException : NotFoundGroupException {
    constructor(message: String) : super(message)

    constructor() : this("존재하지 않는 토론 방 입니다.")
}
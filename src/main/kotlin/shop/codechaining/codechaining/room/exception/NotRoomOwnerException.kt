package shop.codechaining.codechaining.room.exception

import shop.codechaining.codechaining.global.error.exception.AccessDeniedGroupException

class NotRoomOwnerException : AccessDeniedGroupException {
    constructor(message: String) : super(message)

    constructor() : this("본인의 토론 방이 아니면 수정, 삭제할 수 없습니다.")
}
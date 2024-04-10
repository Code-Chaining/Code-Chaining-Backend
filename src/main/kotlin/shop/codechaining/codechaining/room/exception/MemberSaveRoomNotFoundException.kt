package shop.codechaining.codechaining.room.exception

import shop.codechaining.codechaining.global.error.exception.NotFoundGroupException

class MemberSaveRoomNotFoundException : NotFoundGroupException {
    constructor(message: String) : super(message)

    constructor() : this("존재하지 않는 스크랩입니다.")
}
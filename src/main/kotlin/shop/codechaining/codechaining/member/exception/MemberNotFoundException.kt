package shop.codechaining.codechaining.member.exception

import shop.codechaining.codechaining.global.error.exception.NotFoundGroupException

class MemberNotFoundException : NotFoundGroupException {
    constructor(message: String) : super(message)

    constructor() : this("존재하지 않는 사용자입니다.")
}
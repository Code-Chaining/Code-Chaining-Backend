package shop.codechaining.codechaining.comment.exception

import shop.codechaining.codechaining.global.error.exception.NotFoundGroupException

class CommentNotFoundException : NotFoundGroupException {
    constructor(message: String) : super(message)

    constructor() : this("존재하지 않는 댓글입니다.")
}
package shop.codechaining.codechaining.comment.exception

import shop.codechaining.codechaining.global.error.exception.AccessDeniedGroupException

class NotCommentOwnerException : AccessDeniedGroupException {
    constructor(message: String) : super(message)
    
    constructor() : this("본인의 댓글이 아니면 수정, 삭제할 수 없습니다.")
}
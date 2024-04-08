package shop.codechaining.codechaining.comment.api.request

import jakarta.validation.constraints.Size
import shop.codechaining.codechaining.global.annotation.UnicodeNotBlank

data class CommentSaveReqDto(
    val roomId: Long,
    @field:UnicodeNotBlank(message = "제목은 비워둘 수 없습니다.")
    @field:Size(max = 1500, message = "댓글 내용은 1500자를 넘길 수 없습니다.")
    val contents: String
)

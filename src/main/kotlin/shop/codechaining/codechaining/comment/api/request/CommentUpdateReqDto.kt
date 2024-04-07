package shop.codechaining.codechaining.comment.api.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CommentUpdateReqDto(
    @field:NotBlank(message = "댓글 내용은 비워둘 수 없습니다.")
    @field:Size(max = 1500, message = "댓글 내용은 1500자를 넘길 수 없습니다.")
    val contents: String
)

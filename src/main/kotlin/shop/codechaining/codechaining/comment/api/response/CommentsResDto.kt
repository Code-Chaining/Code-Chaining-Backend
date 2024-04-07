package shop.codechaining.codechaining.comment.api.response

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class CommentsResDto(
    val commentList: List<CommentResDto>
)

data class CommentResDto(
    val commentId: Long?,
    val memberId: Long?,
    val nickname: String,
    val picture: String,
    val contents: String,
    val date: String
) {
    companion object {
        private val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")

        fun from(
            commentId: Long?,
            memberId: Long?,
            nickname: String,
            picture: String,
            contents: String,
            date: LocalDateTime
        ): CommentResDto {
            val formattedDate = date.format(formatter)
            return CommentResDto(commentId, memberId, nickname, picture, contents, formattedDate)
        }

    }
}

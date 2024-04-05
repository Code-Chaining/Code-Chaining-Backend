package shop.codechaining.codechaining.comment.api.response

data class CommentsResDto(
    val commentList: List<CommentResDto>
)

data class CommentResDto(
    val commentId: Long?,
    val memberId: Long?,
    val nickname: String,
    val picture: String,
    val contents: String
)

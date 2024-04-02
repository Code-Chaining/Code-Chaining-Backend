package shop.codechaining.codechaining.comment.api.request

data class CommentSaveReqDto(
    val roomId: Long,
    val contents: String
)

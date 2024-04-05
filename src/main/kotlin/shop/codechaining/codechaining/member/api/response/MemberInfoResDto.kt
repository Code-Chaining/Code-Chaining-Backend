package shop.codechaining.codechaining.member.api.response

data class MemberInfoResDto(
    val memberId: Long,
    val email: String,
    val nickname: String,
    val picture: String
)

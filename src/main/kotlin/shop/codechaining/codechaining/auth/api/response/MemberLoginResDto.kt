package shop.codechaining.codechaining.auth.api.response

import shop.codechaining.codechaining.member.domain.Member

data class MemberLoginResDto(
    val findMember: Member
)

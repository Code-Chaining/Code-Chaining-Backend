package shop.codechaining.codechaining.auth.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import shop.codechaining.codechaining.auth.api.response.MemberLoginResDto
import shop.codechaining.codechaining.auth.api.response.UserInfo
import shop.codechaining.codechaining.member.domain.Member
import shop.codechaining.codechaining.member.domain.Role
import shop.codechaining.codechaining.member.domain.repository.MemberRepository

@Service
@Transactional(readOnly = true)
class AuthMemberService(
    private val memberRepository: MemberRepository
) {

    @Transactional
    fun saveUserInfo(userInfo: UserInfo): MemberLoginResDto {
        val member = memberRepository.findByEmail(userInfo.email) ?: createMember(userInfo)

        return MemberLoginResDto(member)
    }

    private fun createMember(userInfo: UserInfo): Member {
        return memberRepository.save(
            Member(
                userInfo.email,
                userInfo.nickname,
                userInfo.picture, Role.ROLE_USER
            )
        )
    }

}
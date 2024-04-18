package shop.codechaining.codechaining.member.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import shop.codechaining.codechaining.member.api.response.MemberInfoResDto
import shop.codechaining.codechaining.member.domain.repository.MemberRepository
import shop.codechaining.codechaining.member.exception.MemberNotFoundException

@Service
@Transactional(readOnly = true)
class MemberService(
    private val memberRepository: MemberRepository
) {
    fun memberInfo(email: String?): MemberInfoResDto? {
        val member = email?.let { memberRepository.findByEmail(it) ?: throw MemberNotFoundException() }

        return member?.memberId?.let { MemberInfoResDto(it, member.email, member.nickname, member.picture) }
    }
}
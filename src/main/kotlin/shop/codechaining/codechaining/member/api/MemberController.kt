package shop.codechaining.codechaining.member.api

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import shop.codechaining.codechaining.global.annotation.AuthenticatedEmail
import shop.codechaining.codechaining.global.annotation.WithAuthentication
import shop.codechaining.codechaining.global.template.RspTemplate
import shop.codechaining.codechaining.member.api.response.MemberInfoResDto
import shop.codechaining.codechaining.member.application.MemberService

@RestController
@RequestMapping("/api/member")
class MemberController(
    private val memberService: MemberService
) {
    @GetMapping("/info")
    @WithAuthentication
    fun memberInfo(@AuthenticatedEmail email: String): RspTemplate<MemberInfoResDto> {
        val memberInfo = memberService.memberInfo(email)
        return RspTemplate(HttpStatus.OK, "사용자 정보", memberInfo)
    }
}
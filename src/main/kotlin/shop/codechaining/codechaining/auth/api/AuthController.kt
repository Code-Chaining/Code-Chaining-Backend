package shop.codechaining.codechaining.auth.api

import jakarta.servlet.http.Cookie
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import shop.codechaining.codechaining.auth.api.request.IdTokenReqDto
import shop.codechaining.codechaining.auth.api.request.RefreshTokenReqDto
import shop.codechaining.codechaining.auth.application.AuthMemberService
import shop.codechaining.codechaining.auth.application.AuthService
import shop.codechaining.codechaining.auth.application.TokenService
import shop.codechaining.codechaining.global.jwt.api.dto.TokenDto
import shop.codechaining.codechaining.global.template.RspTemplate

@RestController
@RequestMapping("/api")
class AuthController(
    private val authService: AuthService,
    private val authMemberService: AuthMemberService,
    private val tokenService: TokenService
) {
    @PostMapping("/kakao/token")
    fun generateAccessToken(@RequestBody idTokenReqDto: IdTokenReqDto): RspTemplate<TokenDto> {
        val userInfo = authService.getUserInfo(idTokenReqDto.idToken)
        val getMemberDto = authMemberService.saveUserInfo(userInfo)
        val getToken = tokenService.getToken(getMemberDto)

//        val responseCookie = ResponseCookie.from("accessToken", getToken.accessToken)
        
        return RspTemplate(HttpStatus.OK, "토큰 발급", getToken)
    }

    @PostMapping("/token/access")
    fun generateAccessToken(@RequestBody refreshTokenReqDto: RefreshTokenReqDto): RspTemplate<TokenDto> {
        val getToken = tokenService.generateAccessToken(refreshTokenReqDto)

        return RspTemplate(HttpStatus.OK, "액세스 토큰 발급", getToken)
    }
}
package shop.codechaining.codechaining.auth.api

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import shop.codechaining.codechaining.auth.api.request.TokenReqDto
import shop.codechaining.codechaining.auth.api.response.UserInfo
import shop.codechaining.codechaining.auth.application.AuthService

@RestController
@RequestMapping("/api/login")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/kakao")
    fun getUserInfo(@RequestBody tokenReqDto: TokenReqDto): UserInfo {
        val userInfo = authService.getUserInfo(tokenReqDto.idToken)

        return UserInfo(userInfo.email, userInfo.nickname, userInfo.picture)
    }
}
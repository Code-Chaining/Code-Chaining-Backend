package shop.codechaining.codechaining.auth.application

import shop.codechaining.codechaining.auth.api.response.UserInfo

interface AuthService {
    fun getUserInfo(idToken: String): UserInfo
}
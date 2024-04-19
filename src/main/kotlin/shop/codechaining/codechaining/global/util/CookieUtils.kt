package shop.codechaining.codechaining.global.util

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

object CookieUtils {

    fun getCookie(request: HttpServletRequest, name: String): Cookie? {
        return request.cookies?.firstOrNull { it.name == name }
    }

    fun addCookie(
        response: HttpServletResponse,
        name: String?,
        value: String?,
        httpOnly: Boolean,
        secure: Boolean,
        maxAge: Int
    ) {
        val cookie = Cookie(name, value)
        cookie.path = "/"
        cookie.isHttpOnly = httpOnly
        cookie.secure = secure
        cookie.maxAge = maxAge
        cookie.setAttribute("SameSite", "None")
        response.addCookie(cookie)
    }

    fun deleteCookie(request: HttpServletRequest, response: HttpServletResponse, name: String) {
        getCookie(request, name)?.apply {
            value = ""
            path = "/"
            maxAge = 0
            response.addCookie(this)
        }
    }

}
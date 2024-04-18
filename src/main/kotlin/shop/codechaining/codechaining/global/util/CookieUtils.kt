package shop.codechaining.codechaining.global.util

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse

object CookieUtils {
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

}
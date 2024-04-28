package shop.codechaining.codechaining.global.csrf.application

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service
import shop.codechaining.codechaining.global.util.CookieUtils
import java.util.*

@Service
class CsrfTokenService {
    companion object {
        private const val CSRF_TOKEN_SESSION_ATTR: String = "CSRF-TOKEN"
    }

    fun createCsrfToken(response: HttpServletResponse): String {
        val csrfToken = UUID.randomUUID().toString()
        CookieUtils.addCookie(response, CSRF_TOKEN_SESSION_ATTR, csrfToken, true, secure = true, maxAge = 60 * 60 * 24)

        return csrfToken
    }

    fun deleteCookie(request: HttpServletRequest, response: HttpServletResponse) {
        CookieUtils.deleteCookie(request, response, CSRF_TOKEN_SESSION_ATTR)
    }

}

package shop.codechaining.codechaining.global.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import shop.codechaining.codechaining.global.annotation.WithAuthentication
import shop.codechaining.codechaining.global.jwt.TokenProvider
import shop.codechaining.codechaining.global.util.JwtAuthConstants.AUTHENTICATION_ATTRIBUTE
import shop.codechaining.codechaining.global.util.JwtAuthConstants.AUTHORIZATION_HEADER
import shop.codechaining.codechaining.global.util.JwtAuthConstants.BEARER_PREFIX

@Component
class AuthenticationInterceptor(
    private val tokenProvider: TokenProvider
) : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler is HandlerMethod) {
            val method = handler.method
            if (method.isAnnotationPresent(WithAuthentication::class.java)) {
                val authentication = request.getAttribute(AUTHENTICATION_ATTRIBUTE)
                val headerAuthentication = request.getHeader(AUTHORIZATION_HEADER)?.let {
                    tokenProvider.getAuthenticationEmail(
                        request.getHeader(AUTHORIZATION_HEADER)
                            .removePrefix(BEARER_PREFIX)
                    )
                }

                if (authentication != headerAuthentication) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "사용자 정보가 일치하지 않습니다.")
                    return false
                } else {
                    request.setAttribute("email", headerAuthentication)
                }
            }
        }

        return true
    }
}
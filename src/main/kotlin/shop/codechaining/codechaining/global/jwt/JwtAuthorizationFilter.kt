package shop.codechaining.codechaining.global.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.GenericFilterBean
import shop.codechaining.codechaining.global.util.JwtAuthConstants.AUTHENTICATION_ATTRIBUTE
import shop.codechaining.codechaining.global.util.JwtAuthConstants.AUTHORIZATION_HEADER
import shop.codechaining.codechaining.global.util.JwtAuthConstants.BEARER_PREFIX

class JwtAuthorizationFilter(
    private val tokenProvider: TokenProvider
) : GenericFilterBean() {

    override fun doFilter(servletRequest: ServletRequest?, servletResponse: ServletResponse?, chain: FilterChain) {
        val request = servletRequest as? HttpServletRequest ?: return
        val response = servletResponse as? HttpServletResponse ?: return

        request.getHeader(AUTHORIZATION_HEADER)?.let { header ->
            if (header.startsWith(BEARER_PREFIX)) {
                val token = header.removePrefix(BEARER_PREFIX)
                if (tokenProvider.validateToken(token)) {
                    val authentication = tokenProvider.getAuthenticationEmail(token)
                    request.setAttribute(AUTHENTICATION_ATTRIBUTE, authentication)
                }
            }
        }

        chain.doFilter(request, response)
    }
}
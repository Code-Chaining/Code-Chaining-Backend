package shop.codechaining.codechaining.global.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JwtAuthorizationFilter(
    private val tokenProvider: TokenProvider
) : OncePerRequestFilter() {

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val BEARER_PREFIX = "Bearer "
    }

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        request.getHeader(AUTHORIZATION_HEADER)?.let { header ->
            if (header.startsWith(BEARER_PREFIX)) {
                header.removePrefix(BEARER_PREFIX).takeIf { tokenProvider.validateToken(it) }?.let { token ->
                    tokenProvider.getAuthentication(token).also { authentication ->
                        SecurityContextHolder.getContext().authentication = authentication
                    }
                }
            }
        }

        filterChain.doFilter(request, response)
    }
}
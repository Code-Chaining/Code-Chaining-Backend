package shop.codechaining.codechaining.global.csrf.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.GenericFilterBean

class CsrfTokenFilter : GenericFilterBean() {
    override fun doFilter(servletRequest: ServletRequest?, servletResponse: ServletResponse?, chain: FilterChain) {
        val request = servletRequest as? HttpServletRequest ?: return
        val response = servletResponse as? HttpServletResponse ?: return
        if (request.method in setOf("POST", "PUT", "DELETE")) {
            val requestHeaderCsrfToken = request.getHeader("X-CSRF-TOKEN")
            val cookieCsrfToken = request.cookies?.firstOrNull { it.name == "CSRF-TOKEN" }?.value

            if (requestHeaderCsrfToken == null && cookieCsrfToken == null) {
                throw RuntimeException("CSRF 토큰이 누락되었습니다.")
            }

            if (requestHeaderCsrfToken != cookieCsrfToken) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "CsrfToken이 일치하지 않습니다.")
                return
            }
        }

        chain.doFilter(request, response)
    }

}
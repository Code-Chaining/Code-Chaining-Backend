package shop.codechaining.codechaining.global.config

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import shop.codechaining.codechaining.global.csrf.filter.CsrfTokenFilter
import shop.codechaining.codechaining.global.jwt.JwtAuthorizationFilter
import shop.codechaining.codechaining.global.jwt.TokenProvider

@Configuration
class FilterConfig(
    private val tokenProvider: TokenProvider
) {
    @Bean
    fun csrfTokenFilterRegistrationBean(): FilterRegistrationBean<CsrfTokenFilter> =
        FilterRegistrationBean<CsrfTokenFilter>().apply {
            filter = CsrfTokenFilter()
            addUrlPatterns(
                "/api/room/*",
                "/api/comment/*",
                "/api/member/*"
            )
            order = 1
        }

    @Bean
    fun jwtFilterRegistrationBean(): FilterRegistrationBean<JwtAuthorizationFilter> =
        FilterRegistrationBean<JwtAuthorizationFilter>().apply {
            filter = JwtAuthorizationFilter(tokenProvider)
            addUrlPatterns(
                "/api/room/*",
                "/api/comment/*",
                "/api/member/*",
            )
            order = 2
        }

}
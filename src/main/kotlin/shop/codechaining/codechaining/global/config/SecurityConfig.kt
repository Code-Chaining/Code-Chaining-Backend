package shop.codechaining.codechaining.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import shop.codechaining.codechaining.global.jwt.JwtAuthorizationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthorizationFilter: JwtAuthorizationFilter
) {
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.invoke {
            csrf { disable() }
            cors { configurationSource = configureCors() }
            authorizeHttpRequests {
                authorize("/api/kakao/token", permitAll)
                authorize("/api/token/access", permitAll)
                authorize("/api/room/public", permitAll)
                authorize("/api/room/{roomId}", permitAll)
                authorize("/api/comment/{roomId}", permitAll)
                authorize("/**", authenticated)
            }
            sessionManagement {
                SessionCreationPolicy.STATELESS
            }
        }

        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun configureCors(): CorsConfigurationSource {
        val config = CorsConfiguration()
        config.allowedOrigins = listOf("*")
        config.allowedMethods = listOf("*")
        config.allowedHeaders = listOf("*")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        return source
    }

}

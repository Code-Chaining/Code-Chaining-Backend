package shop.codechaining.codechaining.global.jwt

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import shop.codechaining.codechaining.global.jwt.api.dto.TokenDto
import java.security.Key
import java.security.SignatureException
import java.util.*

@Component
class TokenProvider {
    private val log = LoggerFactory.getLogger(javaClass)

    @Value("\${token.expire.time.access}")
    lateinit var accessTokenExpireTime: String

    @Value("\${token.expire.time.refresh}")
    lateinit var refreshTokenExpireTime: String

    @Value("\${jwt.secret}")
    lateinit var secret: String

    private lateinit var key: Key

    @PostConstruct
    fun init() {
        key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secret))
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
            return true
        } catch (e: ExpiredJwtException) {
            log.error("JWT is expired: ${e.message}")
            throw e
        } catch (e: SignatureException) {
            log.error("JWT signature does not match: ${e.message}")
            throw e
        } catch (e: Exception) {
            log.error("JWT validation fails: ${e.message}")
            throw e
        }
    }

    fun generateToken(email: String): TokenDto {
        val accessToken = generateAccessToken(email)
        val refreshToken = generateRefreshToken()

        return TokenDto(accessToken, refreshToken)
    }

    fun generateAccessTokenByRefreshToken(email: String, refreshToken: String): TokenDto {
        val accessToken = generateAccessToken(email)

        return TokenDto(accessToken, refreshToken)
    }

    private fun generateAccessToken(email: String): String {
        val now = Date()
        val accessExpiryDate = Date(now.time + accessTokenExpireTime.toLong())

        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(now)
            .setExpiration(accessExpiryDate)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }

    private fun generateRefreshToken(): String {
        val now = Date()
        val refreshExpiryDate = Date(now.time + refreshTokenExpireTime.toLong())

        return Jwts.builder()
            .setExpiration(refreshExpiryDate)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }

    fun getAuthenticationEmail(token: String): String {
        val claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body

        return claims.subject
    }

}
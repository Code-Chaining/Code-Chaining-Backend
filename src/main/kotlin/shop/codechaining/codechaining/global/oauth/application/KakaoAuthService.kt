package shop.codechaining.codechaining.global.oauth.application

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import shop.codechaining.codechaining.auth.api.response.UserInfo
import shop.codechaining.codechaining.auth.application.AuthService
import java.nio.charset.StandardCharsets
import java.util.*

@Service
class KakaoAuthService(private val objectMapper: ObjectMapper) : AuthService {

    companion object {
        private const val JWT_DELIMITER = "\\."
    }

    override fun getUserInfo(idToken: String): UserInfo {
        val decodePayload = getDecodePayload(idToken)

        return try {
            objectMapper.readValue(decodePayload, UserInfo::class.java)
        } catch (e: JsonProcessingException) {
            throw RuntimeException(e)
        }
    }

    private fun getDecodePayload(idToken: String): String {
        val payload = getPayload(idToken)
        return String(Base64.getUrlDecoder().decode(payload), StandardCharsets.UTF_8)
    }

    private fun getPayload(idToken: String): String {
        return idToken.split(JWT_DELIMITER.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
    }

}
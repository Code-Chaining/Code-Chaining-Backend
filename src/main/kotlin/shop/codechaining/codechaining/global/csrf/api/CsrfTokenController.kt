package shop.codechaining.codechaining.global.csrf.api

import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import shop.codechaining.codechaining.global.csrf.application.CsrfTokenService
import shop.codechaining.codechaining.global.template.RspTemplate

@RestController
@RequestMapping("/api")
class CsrfTokenController {

    @GetMapping("/csrf-token")
    fun getCsrfToken(
        response: HttpServletResponse,
    ): RspTemplate<String> {
        val csrfToken = CsrfTokenService.createCsrfToken(response)
        return RspTemplate(HttpStatus.OK, data = csrfToken)
    }
    
}
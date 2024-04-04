package shop.codechaining.codechaining.global.jwt.domain

import jakarta.persistence.*
import shop.codechaining.codechaining.member.domain.Member

@Entity
class Token(
    var refreshToken: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id", nullable = false)
    val tokenId: Long? = null
) {
    fun refreshTokenUpdate(refreshToken: String) {
        if (this.refreshToken != refreshToken) {
            this.refreshToken = refreshToken;
        }
    }
}
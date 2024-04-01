package shop.codechaining.codechaining.member.domain

import jakarta.persistence.*

@Entity
class Member(
    val email: String,
    val name: String,
    val picture: String,
    var nickname: String,
    @Enumerated(EnumType.STRING)
    val role: Role,
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "member_id")
    val memberId: Long? = null
) {
    fun updateNickname(nickname: String) {
        this.nickname = nickname
    }

}

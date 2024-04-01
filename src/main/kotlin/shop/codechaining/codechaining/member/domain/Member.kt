package shop.codechaining.codechaining.member.domain

import jakarta.persistence.*

@Entity
class Member(
    val email: String,

    var name: String,

    val picture: String,

    nickname: String,

    @Enumerated(EnumType.STRING)
    val role: Role,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "member_id")
    val memberId: Long? = null

    var nickname: String = nickname
        private set

    fun updateNickname(nickname: String) {
        this.nickname = nickname
    }

}

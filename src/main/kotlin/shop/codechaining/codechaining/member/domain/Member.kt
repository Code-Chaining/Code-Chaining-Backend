package shop.codechaining.codechaining.member.domain

import jakarta.persistence.*

@Entity
class Member(
    val email: String,
    val nickname: String,
    val picture: String,
    @Enumerated(EnumType.STRING)
    val role: Role,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val memberId: Long? = null
) {

}

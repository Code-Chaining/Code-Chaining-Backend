package shop.codechaining.codechaining.room.domain

import jakarta.persistence.*
import shop.codechaining.codechaining.member.domain.Member
import java.time.LocalDateTime

@Entity
class Room(
    var title: String,
    var codeAndContents: String,
    var date: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    val roomId: Long? = null
) {
    fun updateRoom(title: String, codeAndContents: String) {
        this.title = title
        this.codeAndContents = codeAndContents
    }
}
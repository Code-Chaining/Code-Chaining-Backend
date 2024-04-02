package shop.codechaining.codechaining.comment.domain

import jakarta.persistence.*
import shop.codechaining.codechaining.member.domain.Member
import shop.codechaining.codechaining.room.domain.Room
import java.time.LocalDateTime

@Entity
class Comment(
    var contents: String,
    var date: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var writer: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    var room: Room,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    val commentId: Long? = null
) {
    fun updateComment(contents: String) {
        this.contents = contents
    }

}
package shop.codechaining.codechaining.room.domain

import jakarta.persistence.*
import shop.codechaining.codechaining.member.domain.Member

@Entity
class MemberSaveRoom(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    var room: Room,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_save_room_id", nullable = false)
    var memberSaveRoomId: Long? = null
) {
}
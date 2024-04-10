package shop.codechaining.codechaining.room.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import shop.codechaining.codechaining.member.domain.Member
import shop.codechaining.codechaining.room.domain.MemberSaveRoom
import shop.codechaining.codechaining.room.domain.Room

interface MemberSaveRoomRepository : JpaRepository<MemberSaveRoom, Long> {
    fun findByMemberAndRoom(member: Member, room: Room): MemberSaveRoom?
    fun existsByMemberAndRoom(member: Member, room: Room): Boolean
    fun findAllByMember(member: Member): List<MemberSaveRoom>
}
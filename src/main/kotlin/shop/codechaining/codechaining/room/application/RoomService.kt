package shop.codechaining.codechaining.room.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import shop.codechaining.codechaining.comment.domain.repository.CommentRepository
import shop.codechaining.codechaining.member.domain.repository.MemberRepository
import shop.codechaining.codechaining.member.exception.MemberNotFoundException
import shop.codechaining.codechaining.room.api.request.RoomSaveReqDto
import shop.codechaining.codechaining.room.api.request.RoomUpdateReqDto
import shop.codechaining.codechaining.room.api.request.ScrapReqDto
import shop.codechaining.codechaining.room.api.response.*
import shop.codechaining.codechaining.room.domain.MemberSaveRoom
import shop.codechaining.codechaining.room.domain.Room
import shop.codechaining.codechaining.room.domain.repository.MemberSaveRoomRepository
import shop.codechaining.codechaining.room.domain.repository.RoomRepository
import shop.codechaining.codechaining.room.exception.AlreadyScrappedException
import shop.codechaining.codechaining.room.exception.MemberSaveRoomNotFoundException
import shop.codechaining.codechaining.room.exception.NotRoomOwnerException
import shop.codechaining.codechaining.room.exception.RoomNotFoundException

@Service
@Transactional(readOnly = true)
class RoomService(
    private val roomRepository: RoomRepository,
    private val memberRepository: MemberRepository,
    private val commentRepository: CommentRepository,
    private val memberSaveRoomRepository: MemberSaveRoomRepository
) {
    @Transactional
    fun roomSave(email: String, roomSaveReqDto: RoomSaveReqDto) {
        val member = memberRepository.findByEmail(email) ?: throw MemberNotFoundException()
        roomRepository.save(Room(roomSaveReqDto.title, roomSaveReqDto.codeAndContents, member = member))
    }

    @Transactional
    fun roomUpdate(email: String, roomId: Long, roomUpdateReqDto: RoomUpdateReqDto) {
        val member = memberRepository.findByEmail(email) ?: throw MemberNotFoundException()
        val room = roomRepository.findById(roomId).orElseThrow { RoomNotFoundException() }

        if (room.member.memberId != member.memberId) {
            throw NotRoomOwnerException()
        }

        room.updateRoom(roomUpdateReqDto.title, roomUpdateReqDto.codeAndContents)
    }

    fun roomInfo(email: String?, roomId: Long): RoomInfoResDto? {
        val room = roomRepository.findById(roomId).orElseThrow { RoomNotFoundException() }
        val isScrap = email?.let { it ->
            memberRepository.findByEmail(it)?.let { memberSaveRoomRepository.existsByMemberAndRoom(it, room) }
        } ?: false


        return room.member.memberId?.let {
            RoomInfoResDto.from(
                title = room.title,
                codeAndContents = room.codeAndContents,
                date = room.date,
                memberId = it,
                nickname = room.member.nickname,
                picture = room.member.picture,
                isScrap = isScrap
            )
        }
    }

    fun myRooms(email: String): MyRoomsResDto {
        val member = memberRepository.findByEmail(email) ?: throw MemberNotFoundException()
        val myRoomList = roomRepository.findAllByMemberOrderByRoomIdDesc(member)

        return MyRoomsResDto(myRoomList.map { room: Room ->
            val isScrap = memberSaveRoomRepository.existsByMemberAndRoom(member, room)
            MyRoomResDto(
                room.roomId,
                room.title,
                commentRepository.countByRoom(room),
                isScrap
            )
        })
    }

    fun myScrapRooms(email: String): PublicRoomsResDto {
        val member = memberRepository.findByEmail(email) ?: throw MemberNotFoundException()
        val memberSaveRoomList = memberSaveRoomRepository.findAllByMember(member)

        return PublicRoomsResDto(memberSaveRoomList.map { memberSaveRoom: MemberSaveRoom ->
            val isScrap = memberSaveRoomRepository.existsByMemberAndRoom(member, memberSaveRoom.room)
            PublicRoomResDto(
                memberSaveRoom.room.roomId,
                memberSaveRoom.room.title,
                memberSaveRoom.room.member.nickname,
                commentRepository.countByRoom(memberSaveRoom.room),
                isScrap
            )
        })
    }

    fun publicRooms(email: String?, filter: String? = null): PublicRoomsResDto {
        val publicRoomList = if (!filter.isNullOrEmpty()) {
            roomRepository.findByTitleContainingIgnoreCaseOrderByRoomIdDesc(filter)
        } else {
            roomRepository.findAllByOrderByRoomIdDesc()
        }

        val member = email?.let { memberRepository.findByEmail(it) }

        return PublicRoomsResDto(publicRoomList.map { room: Room ->
            val isScrap = member?.let { memberSaveRoomRepository.existsByMemberAndRoom(it, room) } ?: false
            PublicRoomResDto(
                room.roomId,
                room.title,
                room.member.nickname,
                commentRepository.countByRoom(room),
                isScrap
            )
        })
    }

    @Transactional
    fun deleteMyRoom(email: String, roomId: Long) {
        val member = memberRepository.findByEmail(email) ?: throw MemberNotFoundException()
        val room = roomRepository.findById(roomId).orElseThrow { RoomNotFoundException() }

        if (room.member.memberId != member.memberId) {
            throw NotRoomOwnerException()
        }

        commentRepository.deleteAllByRoom(room)
        roomRepository.delete(room)
    }

    // 토론 방 스크랩
    @Transactional
    fun roomScrap(email: String, scrapReqDto: ScrapReqDto) {
        val member = memberRepository.findByEmail(email) ?: throw MemberNotFoundException()
        val room = roomRepository.findById(scrapReqDto.roomId).orElseThrow { RoomNotFoundException() }

        memberSaveRoomRepository.findByMemberAndRoom(member, room)?.let {
            throw AlreadyScrappedException()
        } ?: memberSaveRoomRepository.save(MemberSaveRoom(member, room))
    }

    // 토론 방 스크랩 삭제
    @Transactional
    fun roomScrapDelete(email: String, scrapReqDto: ScrapReqDto) {
        val member = memberRepository.findByEmail(email) ?: throw MemberNotFoundException()
        val room = roomRepository.findById(scrapReqDto.roomId).orElseThrow { RoomNotFoundException() }

        memberSaveRoomRepository.findByMemberAndRoom(member, room)?.let {
            memberSaveRoomRepository.delete(it)
        } ?: throw MemberSaveRoomNotFoundException("스크랩된 토론 방을 찾을 수 없습니다.")
    }

}
package shop.codechaining.codechaining.room.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import shop.codechaining.codechaining.comment.domain.repository.CommentRepository
import shop.codechaining.codechaining.member.domain.repository.MemberRepository
import shop.codechaining.codechaining.member.exception.MemberNotFoundException
import shop.codechaining.codechaining.room.api.request.RoomSaveReqDto
import shop.codechaining.codechaining.room.api.request.RoomUpdateReqDto
import shop.codechaining.codechaining.room.api.response.*
import shop.codechaining.codechaining.room.domain.Room
import shop.codechaining.codechaining.room.domain.repository.RoomRepository
import shop.codechaining.codechaining.room.exception.NotRoomOwnerException
import shop.codechaining.codechaining.room.exception.RoomNotFoundException

@Service
@Transactional(readOnly = true)
class RoomService(
    private val roomRepository: RoomRepository,
    private val memberRepository: MemberRepository,
    private val commentRepository: CommentRepository
) {
    @Transactional
    fun roomSave(email: String, roomSaveReqDto: RoomSaveReqDto) {
        val member = memberRepository.findByEmail(email).orElseThrow { MemberNotFoundException() }
        roomRepository.save(Room(roomSaveReqDto.title, roomSaveReqDto.codeAndContents, member = member))
    }

    @Transactional
    fun roomUpdate(email: String, roomId: Long, roomUpdateReqDto: RoomUpdateReqDto) {
        val member = memberRepository.findByEmail(email).orElseThrow { MemberNotFoundException() }
        val room = roomRepository.findById(roomId).orElseThrow { RoomNotFoundException() }

        if (room.member.memberId != member.memberId) {
            throw NotRoomOwnerException()
        }

        room.updateRoom(roomUpdateReqDto.title, roomUpdateReqDto.codeAndContents)
    }

    fun roomInfo(roomId: Long): RoomInfoResDto? {
        val room = roomRepository.findById(roomId).orElseThrow { RoomNotFoundException() }

        return room.member.memberId?.let {
            RoomInfoResDto.from(
                title = room.title,
                codeAndContents = room.codeAndContents,
                date = room.date,
                memberId = it,
                nickname = room.member.nickname,
                picture = room.member.picture
            )
        }
    }

    fun myRooms(email: String): MyRoomsResDto {
        val member = memberRepository.findByEmail(email).orElseThrow { MemberNotFoundException() }
        val myRoomList = roomRepository.findAllByMemberOrderByRoomIdDesc(member)

        return MyRoomsResDto(myRoomList.map { room: Room ->
            MyRoomResDto(
                room.roomId,
                room.title,
                commentRepository.countByRoom(room)
            )
        })
    }

    fun publicRooms(filter: String? = null): PublicRoomsResDto {
        val publicRoomList = if (!filter.isNullOrEmpty()) {
            roomRepository.findByTitleContainingIgnoreCaseOrderByRoomIdDesc(filter)
        } else {
            roomRepository.findAllByOrderByRoomIdDesc()
        }

        return PublicRoomsResDto(publicRoomList.map { room: Room ->
            PublicRoomResDto(
                room.roomId,
                room.title,
                room.member.nickname,
                commentRepository.countByRoom(room)
            )
        })
    }

    @Transactional
    fun deleteMyRoom(email: String, roomId: Long) {
        val member = memberRepository.findByEmail(email).orElseThrow { MemberNotFoundException() }
        val room = roomRepository.findById(roomId).orElseThrow { RoomNotFoundException() }

        if (room.member.memberId != member.memberId) {
            throw NotRoomOwnerException()
        }

        commentRepository.deleteAllByRoom(room)
        roomRepository.delete(room)
    }

}
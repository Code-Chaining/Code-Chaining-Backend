package shop.codechaining.codechaining.room.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import shop.codechaining.codechaining.member.domain.repository.MemberRepository
import shop.codechaining.codechaining.member.exception.MemberNotFoundException
import shop.codechaining.codechaining.room.api.request.RoomSaveReqDto
import shop.codechaining.codechaining.room.api.request.RoomUpdateReqDto
import shop.codechaining.codechaining.room.api.response.*
import shop.codechaining.codechaining.room.domain.Room
import shop.codechaining.codechaining.room.domain.repository.RoomRepository

@Service
@Transactional(readOnly = true)
class RoomService(
    private val roomRepository: RoomRepository,
    private val memberRepository: MemberRepository
) {
    @Transactional
    fun roomSave(email: String, roomSaveReqDto: RoomSaveReqDto) {
        val member = memberRepository.findByEmail(email).orElseThrow { MemberNotFoundException() }
        roomRepository.save(Room(roomSaveReqDto.title, roomSaveReqDto.codeAndContents, member = member))
    }

    @Transactional
    fun roomUpdate(email: String, roomId: Long, roomUpdateReqDto: RoomUpdateReqDto) {
        val room = roomRepository.findById(roomId).orElseThrow()

        room.updateRoom(roomUpdateReqDto.title, roomUpdateReqDto.codeAndContents)
    }

    fun roomInfo(roomId: Long): RoomInfoResDto {
        val room = roomRepository.findById(roomId).orElseThrow()

        return RoomInfoResDto.from(
            title = room.title,
            codeAndContents = room.codeAndContents,
            date = room.date
        )
    }

    fun myRooms(email: String): MyRoomsResDto {
        val member = memberRepository.findByEmail(email).orElseThrow { MemberNotFoundException() }
        val myRoomList = roomRepository.findAllByMember(member)

        return MyRoomsResDto(myRoomList.map { room: Room -> MyRoomResDto(room.roomId, room.title) })
    }

    fun publicRooms(email: String): PublicRoomsResDto {
        val member = memberRepository.findByEmail(email).orElseThrow { MemberNotFoundException() }
        val publicRoomList = roomRepository.findAll()

        return PublicRoomsResDto(publicRoomList.map { room: Room ->
            PublicRoomResDto(
                room.roomId,
                room.title,
                room.member.nickname
            )
        })
    }

}
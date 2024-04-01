package shop.codechaining.codechaining.room.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import shop.codechaining.codechaining.member.domain.repository.MemberRepository
import shop.codechaining.codechaining.room.api.request.RoomSaveReqDto
import shop.codechaining.codechaining.room.api.request.RoomUpdateReqDto
import shop.codechaining.codechaining.room.api.response.RoomInfoResDto
import shop.codechaining.codechaining.room.api.response.RoomResDto
import shop.codechaining.codechaining.room.api.response.RoomsResDto
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
        val member = memberRepository.findByEmail(email).orElseThrow()
        roomRepository.save(Room(roomSaveReqDto.title, roomSaveReqDto.codeAndContents, member = member))
    }

    @Transactional
    fun roomUpdate(email: String, roomId: Long, roomUpdateReqDto: RoomUpdateReqDto) {
        val room = roomRepository.findById(roomId).orElseThrow()

        room.updateRoom(roomUpdateReqDto.title, roomUpdateReqDto.codeAndContents)
    }

    fun roomInfo(roomId: Long): RoomInfoResDto {
        val room = roomRepository.findById(roomId).orElseThrow()

        return RoomInfoResDto(
            title = room.title,
            codeAndContents = room.codeAndContents,
            date = room.date
        )
    }

    fun myRooms(email: String): RoomsResDto {
        val member = memberRepository.findByEmail(email).orElseThrow()
        val myRoomList = roomRepository.findAllByMember(member)

        return RoomsResDto(myRoomList.map { room: Room -> RoomResDto(room.roomId, room.title) })
    }

}
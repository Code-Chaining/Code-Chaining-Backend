package shop.codechaining.codechaining.room.api

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import shop.codechaining.codechaining.global.template.RspTemplate
import shop.codechaining.codechaining.room.api.request.RoomSaveReqDto
import shop.codechaining.codechaining.room.api.request.RoomUpdateReqDto
import shop.codechaining.codechaining.room.api.response.RoomInfoResDto
import shop.codechaining.codechaining.room.api.response.RoomsResDto
import shop.codechaining.codechaining.room.application.RoomService

@RestController
@RequestMapping("/api/room")
class RoomController(
    private val roomService: RoomService
) {
    @PostMapping("/")
    fun roomSave(
        @RequestBody roomSaveReqDto: RoomSaveReqDto
    ): RspTemplate<String> {
        roomService.roomSave("email", roomSaveReqDto)
        return RspTemplate(HttpStatus.CREATED, "토론 방 생성")
    }

    @PutMapping("/{roomId}")
    fun roomUpdate(@PathVariable roomId: Long, @RequestBody roomUpdateReqDto: RoomUpdateReqDto): RspTemplate<String> {
        roomService.roomUpdate("email", roomId, roomUpdateReqDto)

        return RspTemplate(HttpStatus.OK, "토론 방 수정")
    }

    @GetMapping("/info")
    fun roomInfo(@RequestParam roomId: Long): RspTemplate<RoomInfoResDto> {
        val roomInfo = roomService.roomInfo(roomId)
        return RspTemplate(HttpStatus.OK, "토론 방 정보", roomInfo)
    }

    @GetMapping("/my")
    fun myRooms(): RspTemplate<RoomsResDto> {
        val myRooms = roomService.myRooms("email")
        return RspTemplate(HttpStatus.OK, "내 토론 방", myRooms)
    }
}
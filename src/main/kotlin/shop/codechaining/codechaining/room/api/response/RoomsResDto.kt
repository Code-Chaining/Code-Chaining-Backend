package shop.codechaining.codechaining.room.api.response

data class RoomsResDto(
    val roomList: List<RoomResDto>
)

data class RoomResDto(
    val roomId: Long?,
    val title: String
)
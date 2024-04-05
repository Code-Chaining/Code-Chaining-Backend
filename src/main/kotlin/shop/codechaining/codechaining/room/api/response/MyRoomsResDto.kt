package shop.codechaining.codechaining.room.api.response

data class MyRoomsResDto(
    val myRoomList: List<MyRoomResDto>
)

data class MyRoomResDto(
    val roomId: Long?,
    val title: String,
    val commentCount: Int
)
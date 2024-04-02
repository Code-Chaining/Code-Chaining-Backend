package shop.codechaining.codechaining.room.api.response

data class PublicRoomsResDto(
    val publicRoomList: List<PublicRoomResDto>
)

data class PublicRoomResDto(
    val roomId: Long?,
    val title: String,
    val writer: String,
//    val commentCount: Int
)


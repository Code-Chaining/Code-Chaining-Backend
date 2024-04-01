package shop.codechaining.codechaining.room.api.response

import java.time.LocalDateTime

data class RoomInfoResDto(
    val title: String,
    val codeAndContents: String,
    val date: LocalDateTime
)

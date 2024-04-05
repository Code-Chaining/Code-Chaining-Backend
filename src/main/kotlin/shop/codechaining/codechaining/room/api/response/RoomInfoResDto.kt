package shop.codechaining.codechaining.room.api.response

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class RoomInfoResDto(
    val title: String,
    val codeAndContents: String,
    val date: String,
    val memberId: Long
) {
    companion object {
        private val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")

        fun from(title: String, codeAndContents: String, date: LocalDateTime, memberId: Long): RoomInfoResDto {
            val formattedDate = date.format(formatter)
            return RoomInfoResDto(title, codeAndContents, formattedDate, memberId)
        }
    }
}

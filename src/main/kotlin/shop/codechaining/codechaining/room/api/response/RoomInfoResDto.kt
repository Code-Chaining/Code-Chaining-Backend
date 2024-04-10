package shop.codechaining.codechaining.room.api.response

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class RoomInfoResDto(
    val title: String,
    val codeAndContents: String,
    val date: String,
    val memberId: Long,
    val nickname: String,
    val picture: String,
    val isScrap: Boolean
) {
    companion object {
        private val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")

        fun from(
            title: String,
            codeAndContents: String,
            date: LocalDateTime,
            memberId: Long,
            nickname: String,
            picture: String,
            isScrap: Boolean
        ): RoomInfoResDto {
            val formattedDate = date.format(formatter)
            return RoomInfoResDto(title, codeAndContents, formattedDate, memberId, nickname, picture, isScrap)
        }
    }
}

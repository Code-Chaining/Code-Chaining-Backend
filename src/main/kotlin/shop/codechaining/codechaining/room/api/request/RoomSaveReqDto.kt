package shop.codechaining.codechaining.room.api.request

import jakarta.validation.constraints.Size
import shop.codechaining.codechaining.global.annotation.UnicodeNotBlank

data class RoomSaveReqDto(
    @field:UnicodeNotBlank(message = "제목은 비워둘 수 없습니다.")
    @field:Size(max = 50, message = "제목은 50자를 넘길 수 없습니다.")
    val title: String,
    @field:UnicodeNotBlank(message = "내용은 비워둘 수 없습니다.")
    @field:Size(max = 3000, message = "내용은 3000자를 넘길 수 없습니다.")
    val codeAndContents: String
)

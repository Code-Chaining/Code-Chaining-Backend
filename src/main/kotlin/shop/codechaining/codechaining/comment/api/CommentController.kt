package shop.codechaining.codechaining.comment.api

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import shop.codechaining.codechaining.comment.api.request.CommentSaveReqDto
import shop.codechaining.codechaining.comment.api.request.CommentUpdateReqDto
import shop.codechaining.codechaining.comment.api.response.CommentsResDto
import shop.codechaining.codechaining.comment.application.CommentService
import shop.codechaining.codechaining.global.template.RspTemplate

@RestController
@RequestMapping("/api/comment")
class CommentController(
    private val commentService: CommentService
) {

    @PostMapping("/")
    fun commentSave(@RequestBody commentSaveReqDto: CommentSaveReqDto): RspTemplate<String> {
        commentService.commentSave("email", commentSaveReqDto)
        return RspTemplate(HttpStatus.CREATED, "댓글 작성")
    }

    @GetMapping("/{roomId}")
    fun roomComments(@PathVariable roomId: Long): RspTemplate<CommentsResDto> {
        val roomComments = commentService.roomComments(roomId)
        return RspTemplate(HttpStatus.OK, "댓글 목록", roomComments)
    }

    @PutMapping("/{commentId}")
    fun commentUpdate(
        @PathVariable commentId: Long,
        @RequestBody commentUpdateReqDto: CommentUpdateReqDto
    ): RspTemplate<String> {
        commentService.commentUpdate("email", commentId, commentUpdateReqDto)
        return RspTemplate(HttpStatus.OK, "댓글 수정")
    }

    @DeleteMapping("/{commentId}")
    fun commentDelete(@PathVariable commentId: Long): RspTemplate<String> {
        commentService.commentDelete("email", commentId)
        return RspTemplate(HttpStatus.OK, "댓글 삭제")
    }

}
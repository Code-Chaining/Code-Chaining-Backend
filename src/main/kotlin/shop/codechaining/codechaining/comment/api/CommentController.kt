package shop.codechaining.codechaining.comment.api

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import shop.codechaining.codechaining.comment.api.request.CommentSaveReqDto
import shop.codechaining.codechaining.comment.api.request.CommentUpdateReqDto
import shop.codechaining.codechaining.comment.api.response.CommentResDto
import shop.codechaining.codechaining.comment.api.response.CommentsResDto
import shop.codechaining.codechaining.comment.application.CommentService
import shop.codechaining.codechaining.global.annotation.AuthenticatedEmail
import shop.codechaining.codechaining.global.annotation.WithAuthentication
import shop.codechaining.codechaining.global.template.RspTemplate

@RestController
@RequestMapping("/api/comment")
class CommentController(
    private val commentService: CommentService
) {

    @PostMapping("/")
    @WithAuthentication
    fun commentSave(
        @AuthenticatedEmail email: String,
        @RequestBody @Valid commentSaveReqDto: CommentSaveReqDto
    ): RspTemplate<CommentResDto> {
        val comment = commentService.commentSave(email, commentSaveReqDto)
        return RspTemplate(HttpStatus.CREATED, "댓글 작성", comment)
    }

    @GetMapping("/{roomId}")
    @WithAuthentication
    fun roomComments(@PathVariable roomId: Long): RspTemplate<CommentsResDto> {
        val roomComments = commentService.roomComments(roomId)
        return RspTemplate(HttpStatus.OK, "댓글 목록", roomComments)
    }

    @PutMapping("/{commentId}")
    @WithAuthentication
    fun commentUpdate(
        @AuthenticatedEmail email: String,
        @PathVariable commentId: Long,
        @RequestBody @Valid commentUpdateReqDto: CommentUpdateReqDto
    ): RspTemplate<String> {
        commentService.commentUpdate(email, commentId, commentUpdateReqDto)
        return RspTemplate(HttpStatus.OK, "댓글 수정")
    }

    @DeleteMapping("/{commentId}")
    @WithAuthentication
    fun commentDelete(
        @AuthenticatedEmail email: String,
        @PathVariable commentId: Long
    ): RspTemplate<String> {
        commentService.commentDelete(email, commentId)
        return RspTemplate(HttpStatus.OK, "댓글 삭제")
    }

}
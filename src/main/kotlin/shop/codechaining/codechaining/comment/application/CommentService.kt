package shop.codechaining.codechaining.comment.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import shop.codechaining.codechaining.comment.api.request.CommentSaveReqDto
import shop.codechaining.codechaining.comment.api.request.CommentUpdateReqDto
import shop.codechaining.codechaining.comment.api.response.CommentResDto
import shop.codechaining.codechaining.comment.api.response.CommentsResDto
import shop.codechaining.codechaining.comment.domain.Comment
import shop.codechaining.codechaining.comment.domain.repository.CommentRepository
import shop.codechaining.codechaining.comment.exception.CommentNotFoundException
import shop.codechaining.codechaining.comment.exception.NotCommentOwnerException
import shop.codechaining.codechaining.member.domain.repository.MemberRepository
import shop.codechaining.codechaining.member.exception.MemberNotFoundException
import shop.codechaining.codechaining.room.domain.repository.RoomRepository
import shop.codechaining.codechaining.room.exception.RoomNotFoundException

@Service
@Transactional(readOnly = true)
class CommentService(
    private val commentRepository: CommentRepository,
    private val memberRepository: MemberRepository,
    private val roomRepository: RoomRepository
) {
    @Transactional
    fun commentSave(email: String, commentSaveReqDto: CommentSaveReqDto): CommentResDto {
        val member = memberRepository.findByEmail(email) ?: throw MemberNotFoundException()
        val room = roomRepository.findById(commentSaveReqDto.roomId).orElseThrow { RoomNotFoundException() }

        val comment = commentRepository.save(Comment(commentSaveReqDto.contents, writer = member, room = room))
        return CommentResDto.from(
            comment.commentId,
            comment.writer.memberId,
            comment.writer.nickname,
            comment.writer.picture,
            comment.contents,
            comment.date
        )
    }

    fun roomComments(roomId: Long): CommentsResDto {
        val room = roomRepository.findById(roomId).orElseThrow { RoomNotFoundException() }
        val comments = commentRepository.findByRoom(room)

        return CommentsResDto(comments.map { comment: Comment ->
            CommentResDto.from(
                comment.commentId,
                comment.writer.memberId,
                comment.writer.nickname,
                comment.writer.picture,
                comment.contents,
                comment.date
            )
        })
    }

    @Transactional
    fun commentUpdate(email: String, commentId: Long, commentUpdateReqDto: CommentUpdateReqDto) {
        val member = memberRepository.findByEmail(email) ?: throw MemberNotFoundException()
        val comment = commentRepository.findById(commentId).orElseThrow { CommentNotFoundException() }

        if (comment.writer.memberId != member.memberId) {
            throw NotCommentOwnerException()
        }

        comment.updateComment(commentUpdateReqDto.contents)
    }

    @Transactional
    fun commentDelete(email: String, commentId: Long) {
        val member = memberRepository.findByEmail(email) ?: throw MemberNotFoundException()
        val comment = commentRepository.findById(commentId).orElseThrow { CommentNotFoundException() }

        if (comment.writer.memberId != member.memberId) {
            throw NotCommentOwnerException()
        }

        commentRepository.delete(comment)
    }

}
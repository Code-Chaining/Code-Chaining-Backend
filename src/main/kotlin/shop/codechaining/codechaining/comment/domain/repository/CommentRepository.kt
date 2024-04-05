package shop.codechaining.codechaining.comment.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import shop.codechaining.codechaining.comment.domain.Comment
import shop.codechaining.codechaining.room.domain.Room

interface CommentRepository : JpaRepository<Comment, Long> {
    fun findByRoom(room: Room): List<Comment>
    fun countByRoom(room: Room): Int
    fun deleteAllByRoom(room: Room)
}
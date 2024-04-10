package shop.codechaining.codechaining.member.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import shop.codechaining.codechaining.member.domain.Member

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByEmail(email: String): Member?
}
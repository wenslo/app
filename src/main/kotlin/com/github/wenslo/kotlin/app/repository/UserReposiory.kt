package com.github.wenslo.kotlin.app.repository

import com.github.wenslo.kotlin.app.entity.sys.User
import com.github.wenslo.kotlin.app.entity.sys.enum.DisableStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserReposiory : JpaRepository<User, Long> {
    fun findByUsername(username: String): List<User>
    fun findByUsernameLike(username: String): List<User>
    fun findByUsernameAndPhone(userName: String, mobile: String): User
    fun findByUsername(userName: String, sort: Sort)
    fun findByStatus(status: DisableStatus, pageable: Pageable): Page<User>
    @Query(value = "from User u where u.phone = ?1 and u.password=?2")
    fun findByPhoneAndPassword(phone: String, password: String): User
}
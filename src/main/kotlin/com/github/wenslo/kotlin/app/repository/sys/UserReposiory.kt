package com.github.wenslo.kotlin.app.repository.sys

import com.github.wenslo.kotlin.app.condition.sys.UserCondition
import com.github.wenslo.kotlin.app.entity.sys.User
import com.github.wenslo.kotlin.app.extension.startWith
import com.github.wenslo.kotlin.app.extension.then
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

interface UserRepository : JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    @Query(value = "from User u where u.phone = ?1 and u.password=?2")
    fun findByPhoneAndPassword(phone: String, password: String): User
}


class UserSpecification(private val c: UserCondition) : Specification<User> {
    override fun toPredicate(root: Root<User>, query: CriteriaQuery<*>, cb: CriteriaBuilder): Predicate {
        val p = mutableListOf<Predicate>()
        c.username.isNotBlank().then { p.add(cb.startWith(root.get<String>(UserCondition::username.name), c.username)) }
        return cb.and(*p.toTypedArray())
    }
}
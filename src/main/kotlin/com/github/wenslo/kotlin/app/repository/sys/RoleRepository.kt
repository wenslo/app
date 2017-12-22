package com.github.wenslo.kotlin.app.repository.sys

import com.github.wenslo.kotlin.app.condition.sys.RoleCondition
import com.github.wenslo.kotlin.app.entity.sys.Role
import com.github.wenslo.kotlin.app.extension.startWith
import com.github.wenslo.kotlin.app.extension.then
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

/**
 * @author: 温海林
 * 2017年12月11日14:11:59
 */
interface RoleRepository : JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
//    fun findByName(name: String): List<Role>
//    fun findByTitle(title: String): List<Role>
}

class RoleSpecification(private val r: RoleCondition) : Specification<Role> {
    override fun toPredicate(root: Root<Role>, query: CriteriaQuery<*>, cb: CriteriaBuilder): Predicate {
        val p = mutableListOf<Predicate>()
        r.name.isNotBlank().then { p.add(cb.startWith(root.get<String>(RoleCondition::name.name), r.name)) }
        r.title.isNotBlank().then { p.add(cb.startWith(root.get<String>(RoleCondition::title.name), r.title)) }
        return cb.and(*p.toTypedArray())
    }

}
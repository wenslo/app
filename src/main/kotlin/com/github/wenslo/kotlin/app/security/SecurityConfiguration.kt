package com.github.wenslo.kotlin.app.security

import org.apache.shiro.cache.CacheManager
import org.apache.shiro.cache.ehcache.EhCacheManager
import org.apache.shiro.mgt.SecurityManager
import org.apache.shiro.spring.LifecycleBeanPostProcessor
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.web.mgt.DefaultWebSecurityManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


/**
 * shiro安全配置
 * @author: 温海林
 * 2017年11月22日16:46:15
 */
@Configuration
class SecurityConfiguration {

    @Bean
    fun securityManager(shiroRealm: ShiroRealm, shiroCacheManager: CacheManager): DefaultWebSecurityManager {
        val stm = DefaultWebSecurityManager()
        stm.setRealm(shiroRealm)
        stm.cacheManager = shiroCacheManager
        return stm
    }

    @Bean
    fun shiroFilter(securityManager: SecurityManager): ShiroFilterFactoryBean {
        var filter = ShiroFilterFactoryBean()
        filter.loginUrl = "login"
        filter.securityManager = securityManager
        filter.filterChainDefinitionMap = filterChainDefinitionMap()
        return filter
    }

    fun filterChainDefinitionMap(): Map<String, String> {
        var map = mutableMapOf<String, String>()
        map.put("/bower_components/**", "anon")
        map.put("/css/**", "anon")
        map.put("/fonts/**", "anon")
        map.put("/img/**", "anon")
        map.put("/js/**", "anon")
//        map.put("/", "anon")
        map.put("/user/**", "perms[user]")
        map.put("/role/**", "perms[role]")
        return map
    }


    /**
     * 领域认证
     */
    @Bean
    fun shiroRealm(): ShiroRealm = ShiroRealm()

    /**
     * shiro缓存
     */
    @Bean
    fun shiroCacheManager(): EhCacheManager {
        var cache = EhCacheManager()
        cache.cacheManagerConfigFile = "classpath:ehcache/ehcache-shiro.xml"
        return cache
    }

    /**
     * 保证shiro内部bean执行
     */
    @Bean
    fun lifecycleBeanPostProcessor(): LifecycleBeanPostProcessor = LifecycleBeanPostProcessor()

    /**
     * shiro 授权
     */
    fun shiroAdvisor(securityManager: DefaultWebSecurityManager): AuthorizationAttributeSourceAdvisor {
        var advisor = AuthorizationAttributeSourceAdvisor()
        advisor.securityManager = securityManager
        return advisor
    }
}
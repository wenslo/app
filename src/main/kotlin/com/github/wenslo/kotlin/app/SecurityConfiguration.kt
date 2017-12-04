package com.github.wenslo.kotlin.app

import com.github.wenslo.kotlin.app.filter.FormAuthenticationFilterExt
import com.github.wenslo.kotlin.app.security.ShiroRealm
import com.google.common.collect.Maps
import org.apache.shiro.cache.CacheManager
import org.apache.shiro.cache.ehcache.EhCacheManager
import org.apache.shiro.mgt.SecurityManager
import org.apache.shiro.spring.LifecycleBeanPostProcessor
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.web.mgt.DefaultWebSecurityManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.servlet.Filter


/**
 * shiro安全配置
 * @author: 温海林
 * 2017年11月22日16:46:15
 */
@Configuration
class SecurityConfiguration {
    companion object {
        val EXT_AUTHC_FILTER_NAME = "extAuthcFilter"
    }

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
        filter.securityManager = securityManager
        filter.filterChainDefinitionMap = filterChainDefinitionMap()
        var filters = Maps.newHashMap<String, Filter>()
        filters.put(EXT_AUTHC_FILTER_NAME, FormAuthenticationFilterExt())
        filter.filters = filters
        return filter
    }

    fun filterChainDefinitionMap(): Map<String, String> {
        var map = mutableMapOf<String, String>()
//        map.put("/login", "anon")
//        map.put("/main", "anon")
//        map.put("/**", EXT_AUTHC_FILTER_NAME)
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
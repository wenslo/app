package com.github.wenslo.kotlin.app

import org.modelmapper.ModelMapper
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter
import org.springframework.web.client.AsyncRestTemplate
import org.springframework.web.filter.CharacterEncodingFilter
import org.springframework.web.filter.DelegatingFilterProxy
import org.springframework.web.multipart.commons.CommonsMultipartResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import java.util.*


/**
 * @author: 温海林
 * 2017年11月23日16:15:17
 */
@EnableAutoConfiguration
@ComponentScan("com.github")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableWebMvc
class ControllerConfiguration : WebMvcConfigurerAdapter() {
    /**
     * data reserve
     */
    @Bean
    fun modelMapper(): ModelMapper = ModelMapper()

    @Bean
    fun template(): AsyncRestTemplate {
        val bean = AsyncRestTemplate()
        val convertes = ArrayList(bean.messageConverters)
        val converter = MappingJackson2HttpMessageConverter()
        converter.supportedMediaTypes = listOf(MediaType.parseMediaType("text/javascript; charset=utf-8"))
        convertes.add(converter)
        bean.messageConverters = convertes
        return bean
    }

    override fun addCorsMappings(registry: CorsRegistry?) {
        registry!!.addMapping("/*")
    }

    override fun configureDefaultServletHandling(configurer: DefaultServletHandlerConfigurer?) {
        configurer!!.enable()
    }

    /**
     * security filter
     * @return
     */
    @Bean
    fun delegatingFilterProxy(): FilterRegistrationBean {
        val bean = FilterRegistrationBean()
        bean.filter = DelegatingFilterProxy()
        bean.urlPatterns = Arrays.asList("/*")
        bean.setName("shiroFilter")
        bean.addInitParameter("targetFilterLifecycle", "true")
        bean.isAsyncSupported = true
        bean.order = 1
        return bean
    }

    @Bean
    fun openEntityManagerInViewFilter(): FilterRegistrationBean {
        val bean = FilterRegistrationBean()
        bean.filter = OpenEntityManagerInViewFilter()
        bean.urlPatterns = Arrays.asList("/*")
        bean.isAsyncSupported = true
        bean.setName("openEntityManagerInViewFilter")
        bean.order = 2
        return bean
    }

    /**
     * 编码filter
     */
    @Bean
    fun characterEncodingFilter(): FilterRegistrationBean {
        val bean = FilterRegistrationBean()
        bean.filter = CharacterEncodingFilter()
        bean.urlPatterns = Arrays.asList("/*")
        bean.addInitParameter("encoding", "UTF-8")
        bean.addInitParameter("forceEncoding", "true")
        bean.isAsyncSupported = true
        bean.setName("characterEncodingFilter")
        bean.order = 3
        return bean
    }

    @Bean
    fun commonsMultipartResolver() = CommonsMultipartResolver()
}
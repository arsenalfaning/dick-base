package com.dick.base.config;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.dick.base.exception.ExceptionProperties;
import com.dick.base.util.BaseConstProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;

@EnableTransactionManagement
@EnableAspectJAutoProxy
@EnableCaching
@Configuration
@EnableConfigurationProperties({ExceptionProperties.class, BaseConstProperties.class})
public class MainConfig {

    @Autowired
    public void initObjectMapper(ObjectMapper objectMapper) {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
        simpleModule.addSerializer(BigDecimal.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
    }

    /**
     * 參數校驗快速失敗
     * @return
     */
    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        return validator;
    }

    /**
     * restTemplate字符編碼
     * @return
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 乐观锁插件
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

//    @Bean(name = "redisTemplate")
//    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        initObjectMapper(objectMapper);
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(redisConnectionFactory);
//        //使用genericJackson2JsonRedisSerializer来序列化和反序列化redis的value值
//        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);
//        template.setValueSerializer(genericJackson2JsonRedisSerializer);
//
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//        //使用StringRedisSerializer来序列化和反序列化redis的key值
//        template.setKeySerializer(stringRedisSerializer);
//        // hash的key也采用String的序列化方式
//        template.setHashKeySerializer(stringRedisSerializer);
//        // value序列化方式采用jackson
//        template.setValueSerializer(genericJackson2JsonRedisSerializer);
//        // hash的value序列化方式采用jackson
//        template.setHashValueSerializer(genericJackson2JsonRedisSerializer);
//        template.afterPropertiesSet();
//        return template;
//    }

}

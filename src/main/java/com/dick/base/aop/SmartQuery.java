package com.dick.base.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * 警告，只有请求线程才能使用
 * 警告，只能用于单表
 * </pre>
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SmartQuery {
    /**
     * model class
     * @return
     */
    Class<?> clazz();

    /**
     * 模糊查询的字段，其他字段均为精准查询
     * @return
     */
    String[] likeFields() default "";
}

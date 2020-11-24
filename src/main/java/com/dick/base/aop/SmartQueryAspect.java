package com.dick.base.aop;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dick.base.util.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Map;

@Aspect
@Component
public class SmartQueryAspect {

    private static final Logger log = LoggerFactory.getLogger(SmartQueryAspect.class);

    @Pointcut("@annotation(SmartQuery)")
    public void smartQueryPointcut() {

    }

    @Around(value = "smartQueryPointcut() && @annotation(smartQuery)", argNames = "joinPoint,smartQuery")
    public Object aroundQuery(ProceedingJoinPoint joinPoint, SmartQuery smartQuery) throws Throwable {
        SmartQueryInfo smartQueryInfo = SmartQueryUtil.get();
        try {
            QueryWrapper queryWrapper = smartQueryInfo.getQueryWrapper();

            queryWrapper.setEntityClass(smartQuery.clazz());
            for (Object arg : joinPoint.getArgs()) {
                if (smartQuery.clazz().isInstance(arg)) {
                    queryWrapper.setEntity(arg);
                } else if (PageInfo.class.isInstance(arg)) {
                    PageInfo pageInfo = (PageInfo) arg;
                    queryWrapper.orderBy( !StringUtils.isEmpty(pageInfo.getProp()), "asc".equals(pageInfo.getOrder()),
                            com.baomidou.mybatisplus.core.toolkit.StringUtils.camelToUnderline(pageInfo.getProp()));
                    smartQueryInfo.setPage(new Page<>(pageInfo.getPage(), pageInfo.getSize()));
                }
            }
            Map<String, String[]> paramMap = ServletUtil.currentRequest().getParameterMap();
            for (String key : paramMap.keySet()) {
                if (key.endsWith("1") || key.endsWith("2")) {
                    String field = key.substring(0, key.length() - 1);
                    String[] values = paramMap.get(key);
                    if (values != null && values.length > 0 && !StringUtils.isEmpty(values[0])) {
                        try {
                            Object value = TypeUtil.convertFromString(values[0], smartQuery.clazz().getDeclaredField(field).getType()) ;
                            String column = com.baomidou.mybatisplus.core.toolkit.StringUtils.camelToUnderline(field);
                            if (key.endsWith("1")) {
                                queryWrapper.ge(column, value);
                            } else {
                                queryWrapper.le(column, value);
                            }
                        } catch (NoSuchFieldException e) {
                            log.warn("field {} not found for class {}", field, smartQuery.clazz());
                        }
                    }
                }
            }
            if (smartQuery.likeFields() != null && queryWrapper.getEntity() != null) {
                for (String field : smartQuery.likeFields()) {
                    if (!StringUtils.isEmpty(field)) {
                        Field f = smartQuery.clazz().getDeclaredField(field);
                        f.setAccessible(true);
                        Object value = f.get(queryWrapper.getEntity());
                        if (value != null) {
                            queryWrapper.like(com.baomidou.mybatisplus.core.toolkit.StringUtils.camelToUnderline(field), value);
                            f.set(queryWrapper.getEntity(), null);
                        }
                    }
                }
            }

            Object result = joinPoint.proceed(joinPoint.getArgs());
            return result;
        } catch (Throwable e) {
            throw e;
        } finally {
            SmartQueryUtil.remove();
        }
    }
}

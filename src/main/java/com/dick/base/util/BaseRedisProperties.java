package com.dick.base.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "base.redis")
@Data
public class BaseRedisProperties {

    public static BaseRedisProperties getInstance() {
        return SpringContextHolder.getBean(BaseRedisProperties.class);
    }
    private String loginToken;
    private String passwordResetToken;
}

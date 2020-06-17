package com.dick.base.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "base.const")
@Data
public class BaseConstProperties {

    public static BaseConstProperties getInstance() {
        return SpringContextHolder.getBean(BaseConstProperties.class);
    }

    private String tokenName;

}

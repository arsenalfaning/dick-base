package com.dick.base.exception;

import com.dick.base.util.SpringContextHolder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "base.exception")
@Data
public class ExceptionProperties {

    public static ExceptionProperties getInstance() {
        return SpringContextHolder.getBean(ExceptionProperties.class);
    }
    private String systemError;
    private String usernameDuplicated;
    private String usernameOrPasswordError;
}

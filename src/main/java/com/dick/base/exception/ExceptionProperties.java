package com.dick.base.exception;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "base.exception")
@Data
public class ExceptionProperties {
    private String systemError;
}

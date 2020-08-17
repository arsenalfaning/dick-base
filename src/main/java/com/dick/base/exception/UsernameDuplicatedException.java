package com.dick.base.exception;

import com.dick.base.util.SpringContextHolder;
import org.springframework.http.HttpStatus;

public class UsernameDuplicatedException extends BaseRuntimeException{
    public UsernameDuplicatedException() {
        super(SpringContextHolder.getBean(ExceptionProperties.class).getUsernameDuplicated());
        status = HttpStatus.BAD_REQUEST;
    }
}

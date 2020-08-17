package com.dick.base.exception;

import com.dick.base.util.ServletUtil;
import com.dick.base.util.SpringContextHolder;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;

public class BaseRuntimeException extends RuntimeException{

    private static final String code = SpringContextHolder.getBean(ExceptionProperties.class).getSystemError();
    private static final MessageSource messageSource = SpringContextHolder.getBean(MessageSource.class);

    protected HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public BaseRuntimeException() {
        super(code);
    }
    public BaseRuntimeException(String message) {
        super(message);
    }
    public BaseRuntimeException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    @Override
    public String getLocalizedMessage() {
        try {
            return messageSource.getMessage(getMessage(), null, ServletUtil.currentRequest().getLocale());
        } catch (NoSuchMessageException e) {

        }
        return getMessage();
    }
}

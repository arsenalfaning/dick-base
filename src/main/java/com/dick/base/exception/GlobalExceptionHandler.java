package com.dick.base.exception;

import com.dick.base.util.BaseResult;
import com.dick.base.util.LogUtil;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@RestControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private final ExceptionProperties exceptionProperties;
    private final MessageSource messageSource;

    public GlobalExceptionHandler(ExceptionProperties exceptionProperties, MessageSource messageSource) {
        this.exceptionProperties = exceptionProperties;
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<BaseResult<Void>> exceptionHandler(Exception e, HttpServletRequest request) {
        LogUtil.log.error("fetal error!", e);
        return new ResponseEntity<>(BaseResult.errorResult(messageSource.getMessage(exceptionProperties.getSystemError(),
                null, request.getLocale())), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = BaseRuntimeException.class)
    public ResponseEntity<BaseResult<Void>> baseRuntimeExceptionHandle(BaseRuntimeException e) {
        LogUtil.log.error("base error!", e);
        return new ResponseEntity<>(BaseResult.errorResult(e.getLocalizedMessage()), e.status);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResult<Void>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        LogUtil.log.error("request error!", e);
        return new ResponseEntity<>(BaseResult.errorResult(e.getBindingResult().getFieldError().getDefaultMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<BaseResult<Void>> bindExceptionHandler(BindException e) {
        LogUtil.log.error("request error!", e);
        return new ResponseEntity<>(BaseResult.errorResult(e.getBindingResult().getFieldError().getDefaultMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ServletRequestBindingException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<BaseResult<Void>> badRequestHandler(Exception e) {
        LogUtil.log.error("request error!", e);
        return new ResponseEntity<>(BaseResult.errorResult(e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<BaseResult<Void>> accessDeniedExceptionHandler(AccessDeniedException e) {
        LogUtil.log.error("request error!", e);
        return new ResponseEntity<>(BaseResult.errorResult(e.getLocalizedMessage()), HttpStatus.FORBIDDEN);
    }

    public String getLocaleMessage(String code, Locale locale) {
        return messageSource.getMessage(code, null, locale);
    }

}

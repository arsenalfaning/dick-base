package com.dick.base.exception;

import com.dick.base.util.BaseResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BaseRuntimeException.class)
    public ResponseEntity<BaseResult<Void>> baseRuntimeExceptionHandle(BaseRuntimeException e) {
        return new ResponseEntity<>(BaseResult.errorResult(e.getLocalizedMessage()), e.status);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResult<Void>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(BaseResult.errorResult(e.getBindingResult().getFieldError().getDefaultMessage()), HttpStatus.BAD_REQUEST);
    }
}

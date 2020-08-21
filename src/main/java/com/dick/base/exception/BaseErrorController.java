package com.dick.base.exception;

import com.dick.base.util.BaseResult;
import com.dick.base.util.LogUtil;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@RestController
public class BaseErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    private final ErrorAttributes errorAttributes;
    private final MessageSource messageSource;

    public BaseErrorController(ErrorAttributes errorAttributes, MessageSource messageSource) {
        this.errorAttributes = errorAttributes;
        this.messageSource = messageSource;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping(path = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResult<Map<String, Object>> error(WebRequest request) {
        BaseResult<Map<String, Object>> result = new BaseResult<>();
        result.setMessage(messageSource.getMessage(ExceptionProperties.getInstance().getSystemError(), null, request.getLocale()));
        result.setData(errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.defaults()));
        LogUtil.log.error("error fallback", errorAttributes.getError(request));
        return result;
    }
}

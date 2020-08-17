package com.dick.base.test;

import com.dick.base.exception.ErrUtil;
import com.dick.base.session.dto.SignInParameter;
import com.dick.base.session.dto.SignInResult;
import com.dick.base.util.BaseConstProperties;
import com.dick.base.util.BaseResult;
import com.dick.base.util.DateUtil;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class TestController {

    private final BaseConstProperties baseConstProperties;

    public TestController(BaseConstProperties baseConstProperties) {
        this.baseConstProperties = baseConstProperties;
    }

    @GetMapping("test/token-name")
    public Object test() {
        return baseConstProperties.getTokenName();
    }

    @GetMapping("test/err")
    public Object test1() {
        throw ErrUtil.Default;
    }

    @PostMapping("test/sign-in")
    public Object test2(@RequestBody @Valid SignInParameter signInParameter) {
        return BaseResult.voidResult();
    }

    @GetMapping("test/time")
    public SignInResult test3(@Valid SignInParameter signInParameter) {
        SignInResult result = new SignInResult();
        result.setTime(DateUtil.now());
        return result;
    }
}

package com.dick.base.test;

import com.dick.base.exception.ErrUtil;
import com.dick.base.session.dto.SignInParameter;
import com.dick.base.util.BaseConstProperties;
import com.dick.base.util.BaseResult;
import com.dick.base.util.DateUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;

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

    @GetMapping("api/test/time")
    @PreAuthorize("hasAuthority(#signInParameter.username)")
    public LocalDateTime test3(@Valid SignInParameter signInParameter) {
        return DateUtil.now();
    }
}

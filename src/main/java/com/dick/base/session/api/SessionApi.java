package com.dick.base.session.api;

import com.dick.base.session.dto.SignInParameter;
import com.dick.base.session.dto.SignInResult;
import com.dick.base.session.dto.SignUpParameter;
import com.dick.base.session.service.SessionService;
import com.dick.base.util.BaseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class SessionApi {

    private SessionService sessionService;

    public SessionApi(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("public/sign-up")
    public BaseResult<Void> signUp(@RequestBody @Valid SignUpParameter parameter) {
        sessionService.signUp(parameter);
        return BaseResult.voidResult();
    }

    @PostMapping("public/sign-in")
    public BaseResult<SignInResult> signIn(@RequestBody @Valid SignInParameter parameter) {
        return BaseResult.of(sessionService.signIn(parameter));
    }

    @PostMapping("api/logout")
    public BaseResult<Void> logout() {
        sessionService.logout();
        return BaseResult.voidResult();
    }
}

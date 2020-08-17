package com.dick.base.session.api;

import com.dick.base.session.dto.SignUpParameter;
import com.dick.base.session.service.SessionService;
import com.dick.base.util.BaseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("public")
public class SessionApi {

    private SessionService sessionService;

    public SessionApi(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("sign-up")
    public BaseResult<Void> signUp(@RequestBody @Valid SignUpParameter parameter) {
        sessionService.signUp(parameter);
        return BaseResult.voidResult();
    }
}

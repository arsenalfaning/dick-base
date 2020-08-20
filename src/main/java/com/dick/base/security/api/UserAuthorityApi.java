package com.dick.base.security.api;

import com.dick.base.service.UserAuthorityService;
import com.dick.base.util.BaseResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users/authorities")
public class UserAuthorityApi {

    private UserAuthorityService userAuthorityService;

    public UserAuthorityApi(UserAuthorityService userAuthorityService) {
        this.userAuthorityService = userAuthorityService;
    }

    @PostMapping("grant/{userId}/{authorityId}")
    public BaseResult<Void> grant(@PathVariable Long userId, @PathVariable Integer authorityId) {
        userAuthorityService.grantAuthorityToUser(authorityId, userId);
        return BaseResult.voidResult();
    }

    @PostMapping("revoke/{userId}/{authorityId}")
    public BaseResult<Void> revoke(@PathVariable Long userId, @PathVariable Integer authorityId) {
        userAuthorityService.revokeAuthorityFromUser(authorityId, userId);
        return BaseResult.voidResult();
    }
}

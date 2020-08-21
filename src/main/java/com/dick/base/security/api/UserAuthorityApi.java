package com.dick.base.security.api;

import com.dick.base.service.UserAuthorityService;
import com.dick.base.util.BaseResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users/authorities")
public class UserAuthorityApi {

    private UserAuthorityService userAuthorityService;

    public UserAuthorityApi(UserAuthorityService userAuthorityService) {
        this.userAuthorityService = userAuthorityService;
    }

    @GetMapping("all/{userId}")
    public BaseResult<List<Integer>> getAuthorityIdByUserId(@PathVariable Long userId) {
        return BaseResult.of(userAuthorityService.getAuthorityIdByUser(userId));
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

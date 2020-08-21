package com.dick.base.security.api;

import com.dick.base.model.BaseRole;
import com.dick.base.service.UserRoleService;
import com.dick.base.util.BaseResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users/roles")
public class UserRoleApi {

    private UserRoleService userRoleService;

    public UserRoleApi(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    /**
     * 获取某用户的所有角色
     * @param userId
     * @return
     */
    @GetMapping("{userId}")
    public BaseResult<List<BaseRole>> getRolesByUserId(@PathVariable Long userId) {
        return BaseResult.of(userRoleService.getRolesByUserId(userId));
    }

    /**
     * 授予角色给某用户
     * @param userId
     * @param roleId
     * @return
     */
    @PostMapping("grant/{userId}/{roleId}")
    public BaseResult<Void> grantRoleToUser(@PathVariable Long userId, @PathVariable Integer roleId) {
        userRoleService.grantRoleToUser(roleId, userId);
        return BaseResult.voidResult();
    }

    /**
     * 撤销某用户的角色
     * @param userId
     * @param roleId
     * @return
     */
    @PostMapping("revoke/{userId}/{roleId}")
    public BaseResult<Void> revokeRoleFromUser(@PathVariable Long userId, @PathVariable Integer roleId) {
        userRoleService.revokeRoleFromUser(roleId, userId);
        return BaseResult.voidResult();
    }
}

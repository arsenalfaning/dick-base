package com.dick.base.security.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dick.base.model.BaseRole;
import com.dick.base.security.dto.RoleAddParameter;
import com.dick.base.security.dto.RoleModifyParameter;
import com.dick.base.service.AuthorityService;
import com.dick.base.util.BaseResult;
import com.dick.base.util.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/roles")
public class RoleApi {

    private AuthorityService authorityService;

    public RoleApi(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    /**
     * 角色安页查询
     * @param pageInfo
     * @param baseRole
     * @return
     */
    @GetMapping
    public BaseResult<IPage<BaseRole>> rolePage(PageInfo pageInfo, BaseRole baseRole) {
        return BaseResult.of(authorityService.rolePage(pageInfo, baseRole));
    }

    /**
     * 角色添加
     * @param parameter
     * @return
     */
    @PostMapping
    public BaseResult<BaseRole> addRole(@RequestBody @Valid RoleAddParameter parameter) {
        return BaseResult.of(authorityService.addRole(parameter));
    }

    /**
     * 角色修改
     * @param parameter
     * @return
     */
    @PostMapping("modify")
    public BaseResult<BaseRole> modifyRole(@RequestBody @Valid RoleModifyParameter parameter) {
        return BaseResult.of(authorityService.modifyRole(parameter));
    }

    /**
     * 角色删除
     * @param id
     * @return
     */
    @PostMapping("remove/{id}")
    public BaseResult<Void> removeRole(@PathVariable Integer id) {
        authorityService.removeRole(id);
        return BaseResult.voidResult();
    }
}

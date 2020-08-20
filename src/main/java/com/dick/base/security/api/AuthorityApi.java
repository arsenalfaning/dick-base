package com.dick.base.security.api;

import com.dick.base.model.BaseAuthority;
import com.dick.base.security.AuthorityNode;
import com.dick.base.security.dto.AuthorityAddParameter;
import com.dick.base.security.dto.AuthorityModifyParameter;
import com.dick.base.service.AuthorityService;
import com.dick.base.util.BaseResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 权限管理api
 */
@RestController
@RequestMapping("api/authorities")
public class AuthorityApi {

    private AuthorityService authorityService;

    public AuthorityApi(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @GetMapping("tree")
    public BaseResult<List<AuthorityNode>> authorityTree() {
        return BaseResult.of(authorityService.authorityTree());
    }

    @PostMapping
    public BaseResult<BaseAuthority> addAuthority(@RequestBody @Valid AuthorityAddParameter parameter) {
        return BaseResult.of(authorityService.addAuthority(parameter));
    }

    @PostMapping("modify")
    public BaseResult<BaseAuthority> modifyAuthority(@RequestBody @Valid AuthorityModifyParameter parameter) {
        return BaseResult.of(authorityService.modifyAuthority(parameter));
    }

    @PostMapping("delete/{id}")
    public BaseResult<Void> removeAuthority(@PathVariable Integer id) {
        authorityService.removeAuthority(id);
        return BaseResult.voidResult();
    }
}

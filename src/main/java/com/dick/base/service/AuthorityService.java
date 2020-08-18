package com.dick.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dick.base.aop.SmartQuery;
import com.dick.base.mapper.BaseAuthorityMapper;
import com.dick.base.mapper.BaseRoleMapper;
import com.dick.base.model.BaseAuthority;
import com.dick.base.model.BaseRole;
import com.dick.base.util.PageInfo;
import com.dick.base.util.SmartQueryUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {

    private BaseRoleMapper baseRoleMapper;
    private BaseAuthorityMapper baseAuthorityMapper;

    public AuthorityService(BaseRoleMapper baseRoleMapper, BaseAuthorityMapper baseAuthorityMapper) {
        this.baseRoleMapper = baseRoleMapper;
        this.baseAuthorityMapper = baseAuthorityMapper;
    }

    @SmartQuery(clazz = BaseRole.class, likeFields = "name")
    public IPage<BaseRole> rolePage(PageInfo pageInfo, BaseRole baseRole) {
        return baseRoleMapper.selectPage(SmartQueryUtil.getPage(), SmartQueryUtil.getQueryWrapper());
    }

    @SmartQuery(clazz = BaseAuthority.class, likeFields = "name")
    public IPage<BaseAuthority> authorityPage(PageInfo pageInfo, BaseAuthority baseAuthority) {
        return baseAuthorityMapper.selectPage(SmartQueryUtil.getPage(), SmartQueryUtil.getQueryWrapper());
    }
}

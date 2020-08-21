package com.dick.base.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dick.base.mapper.BaseRoleMapper;
import com.dick.base.mapper.BaseUserRoleMapper;
import com.dick.base.model.BaseRole;
import com.dick.base.model.BaseUserRole;
import com.dick.base.session.service.SessionService;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleService {

    private BaseUserRoleMapper baseUserRoleMapper;
    private BaseRoleMapper baseRoleMapper;
    private SessionService sessionService;

    public UserRoleService(BaseUserRoleMapper baseUserRoleMapper, BaseRoleMapper baseRoleMapper, SessionService sessionService) {
        this.baseUserRoleMapper = baseUserRoleMapper;
        this.baseRoleMapper = baseRoleMapper;
        this.sessionService = sessionService;
    }

    /**
     * 赋予角色给某用户
     * @param roleId
     * @param userId
     */
    public void grantRoleToUser(Integer roleId, Long userId) {
        BaseUserRole userRole = new BaseUserRole();
        userRole.setRoleId(roleId);
        userRole.setUserId(userId);
        baseUserRoleMapper.insert(userRole);
        sessionService.refreshUserBaseInfoToRedisByUserId(userId);
    }

    /**
     * 撤销某用户的角色
     * @param roleId
     * @param userId
     */
    public void revokeRoleFromUser(Integer roleId, Long userId) {
        BaseUserRole userRole = new BaseUserRole();
        userRole.setRoleId(roleId);
        userRole.setUserId(userId);
        baseUserRoleMapper.delete(new QueryWrapper<>(userRole));
        sessionService.refreshUserBaseInfoToRedisByUserId(userId);
    }

    /**
     * 获取某用户的所有角色
     * @param userId
     * @return
     */
    public List<BaseRole> getRolesByUserId(Long userId) {
        List<BaseRole> baseRoles = new LinkedList<>();
        BaseUserRole userRole = new BaseUserRole();
        userRole.setUserId(userId);
        List<BaseUserRole> userRoles = baseUserRoleMapper.selectList(new QueryWrapper<>(userRole));
        if (!userRoles.isEmpty()) {
            QueryWrapper<BaseRole> wrapper = new QueryWrapper<>();
            wrapper.in("id", userRoles.stream().map(r -> r.getId()).collect(Collectors.toList()));
            baseRoles.addAll(baseRoleMapper.selectList(wrapper));
        }
        return baseRoles;
    }
}

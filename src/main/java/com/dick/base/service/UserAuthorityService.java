package com.dick.base.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dick.base.mapper.BaseUserAuthorityMapper;
import com.dick.base.model.BaseUserAuthority;
import com.dick.base.session.service.SessionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAuthorityService {

    private BaseUserAuthorityMapper baseUserAuthorityMapper;
    private SessionService sessionService;

    public UserAuthorityService(BaseUserAuthorityMapper baseUserAuthorityMapper, SessionService sessionService) {
        this.baseUserAuthorityMapper = baseUserAuthorityMapper;
        this.sessionService = sessionService;
    }

    /**
     * 获取用户所有的权限
     * @param userId
     * @return
     */
    public List<Integer> getAuthorityIdByUser(Long userId) {
        BaseUserAuthority userAuthority = new BaseUserAuthority();
        userAuthority.setUserId(userId);
        return baseUserAuthorityMapper.selectList(new QueryWrapper<>(userAuthority)).stream().map(e -> e.getAuthorityId())
                .collect(Collectors.toList());
    }

    /**
     * 赋予权限
     * <pre>
     *     1.插入数据
     *     2.更新缓存
     * </pre>
     * @param authorityId
     * @param userId
     */
    public void grantAuthorityToUser(Integer authorityId, Long userId) {
        BaseUserAuthority userAuthority = new BaseUserAuthority();
        userAuthority.setUserId(userId);
        userAuthority.setAuthorityId(authorityId);
        baseUserAuthorityMapper.insert(userAuthority);
        sessionService.refreshUserBaseInfoToRedisByUserId(userId);
    }

    /**
     * 收回权限
     * <pre>
     *     1.删除数据
     *     2.更新缓存
     * </pre>
     * @param authorityId
     * @param userId
     */
    public void revokeAuthorityFromUser(Integer authorityId, Long userId) {
        BaseUserAuthority userAuthority = new BaseUserAuthority();
        userAuthority.setUserId(userId);
        userAuthority.setAuthorityId(authorityId);
        baseUserAuthorityMapper.delete(new QueryWrapper<>(userAuthority));
        sessionService.refreshUserBaseInfoToRedisByUserId(userId);
    }
}

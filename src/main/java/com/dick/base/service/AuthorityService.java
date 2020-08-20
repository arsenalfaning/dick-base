package com.dick.base.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dick.base.aop.SmartQuery;
import com.dick.base.mapper.*;
import com.dick.base.model.*;
import com.dick.base.security.AuthorityNode;
import com.dick.base.security.dto.AuthorityAddParameter;
import com.dick.base.security.dto.RoleAddParameter;
import com.dick.base.util.PageInfo;
import com.dick.base.util.SmartQueryUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.*;

@Service
public class AuthorityService {

    private BaseRoleMapper baseRoleMapper;
    private BaseAuthorityMapper baseAuthorityMapper;
    private BaseAuthorityPathMapper baseAuthorityPathMapper;
    private BaseUserRoleMapper baseUserRoleMapper;
    private BaseUserAuthorityMapper baseUserAuthorityMapper;

    public AuthorityService(BaseRoleMapper baseRoleMapper, BaseAuthorityMapper baseAuthorityMapper,
                            BaseAuthorityPathMapper baseAuthorityPathMapper, BaseUserRoleMapper baseUserRoleMapper,
                            BaseUserAuthorityMapper baseUserAuthorityMapper) {
        this.baseRoleMapper = baseRoleMapper;
        this.baseAuthorityMapper = baseAuthorityMapper;
        this.baseAuthorityPathMapper = baseAuthorityPathMapper;
        this.baseUserRoleMapper = baseUserRoleMapper;
        this.baseUserAuthorityMapper = baseUserAuthorityMapper;
    }

    /**
     * 角色分页查询
     * @param pageInfo
     * @param baseRole
     * @return
     */
    @SmartQuery(clazz = BaseRole.class, likeFields = "name")
    public IPage<BaseRole> rolePage(PageInfo pageInfo, BaseRole baseRole) {
        return baseRoleMapper.selectPage(SmartQueryUtil.getPage(), SmartQueryUtil.getQueryWrapper());
    }

    /**
     * 角色添加
     * @param parameter
     * @return
     */
    public BaseRole addRole(@RequestBody @Valid RoleAddParameter parameter) {
        BaseRole role = new BaseRole();
        role.setName(parameter.getName());
        role.setRoleCode(parameter.getRoleCode());
        baseRoleMapper.insert(role);
        return role;
    }

    /**
     * 角色删除
     * <pre>
     *     1.删除role
     *     2.删除关系
     * </pre>
     * @param id
     */
    @Transactional
    public void removeRole(Integer id) {
        baseRoleMapper.deleteById(id);
        BaseUserRole userRole = new BaseUserRole();
        userRole.setRoleId(id);
        baseUserRoleMapper.delete(new QueryWrapper<>(userRole));
    }

    /**
     * 返回权限树
     * @return
     */
    public List<AuthorityNode> authorityTree() {
        List<AuthorityNode> tree = new LinkedList<>();
        BaseAuthorityPath path = new BaseAuthorityPath();
        path.setDistance(1);
        List<BaseAuthorityPath> pathList = baseAuthorityPathMapper.selectList(new QueryWrapper<>(path));
        List<BaseAuthority> authorityList = baseAuthorityMapper.selectList(null);
        Map<Integer, AuthorityNode> nodeMap = new LinkedHashMap<>(authorityList.size() * 2 + 1);
        authorityList.forEach( baseAuthority -> {
            AuthorityNode node = new AuthorityNode();
            node.setId(baseAuthority.getId());
            node.setAuthorityCode(baseAuthority.getAuthorityCode());
            node.setAuthorityType(baseAuthority.getAuthorityType());
            node.setName(baseAuthority.getName());
            nodeMap.put(baseAuthority.getId(), node);
        });
        Set<Integer> rootSet = new LinkedHashSet<>(nodeMap.keySet());
        pathList.forEach( baseAuthorityPath -> {
            AuthorityNode ancestor = nodeMap.get(baseAuthorityPath.getAncestor());
            AuthorityNode descendant = nodeMap.get(baseAuthorityPath.getDescendant());
            if (ancestor != null && descendant != null) {
                ancestor.getChildren().add(descendant);
                rootSet.remove(descendant.getId());
            }
        });
        rootSet.forEach( id -> {
            AuthorityNode node = nodeMap.get(id);
            if (node != null) {
                tree.add(node);
            }
        });
        return tree;
    }

    /**
     * 新增权限
     * @param parameter
     * @return
     */
    @Transactional
    public BaseAuthority addAuthority(AuthorityAddParameter parameter) {
        BaseAuthority authority = new BaseAuthority();
        authority.setAuthorityType(parameter.getAuthorityType());
        authority.setName(parameter.getName());
        authority.setAuthorityCode(parameter.getAuthorityCode());
        baseAuthorityMapper.insert(authority);
        if (parameter.getParentId() != null && parameter.getParentId() > 0) {
            baseAuthorityPathMapper.addPath(authority.getId(), parameter.getParentId());
        } else {
            baseAuthorityPathMapper.insert(buildPath(authority.getId(), authority.getId(), 0));
        }
        return authority;
    }

    /**
     * 删除权限
     * <pre>
     *     1.逻辑删除数据
     *     2.物理删除关系
     * </pre>
     * @param id
     */
    @Transactional
    public void removeAuthority(Integer id) {
        baseAuthorityMapper.deleteById(id);
        QueryWrapper<BaseAuthorityPath> wrapper = new QueryWrapper<>();
        wrapper.eq("ancestor", id).or().eq("descendant", id);
        baseAuthorityPathMapper.delete(wrapper);
        BaseUserAuthority userAuthority = new BaseUserAuthority();
        userAuthority.setAuthorityId(id);
        baseUserAuthorityMapper.delete(new QueryWrapper<>(userAuthority));
    }

    private BaseAuthorityPath buildPath(Integer ancestor, Integer descendant, Integer distance) {
        BaseAuthorityPath path = new BaseAuthorityPath();
        path.setDistance(distance);
        path.setAncestor(ancestor);
        path.setDescendant(descendant);
        return path;
    }

    public Set<String> getRoleSetByUserId(Long userId) {
        Set<String> roleSet = new TreeSet<>();
        baseRoleMapper.findByUserId(userId).forEach(r -> {
            roleSet.add(r.getRoleCode());
        });
        return roleSet;
    }

    public Set<String> getAuthoritySetByUserId(Long userId) {
        Set<String> authoritySet = new TreeSet<>();
        baseAuthorityMapper.findByUserId(userId).forEach(r -> authoritySet.add(r.getAuthorityCode()));
        return authoritySet;
    }
}

package com.dick.base.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dick.base.aop.SmartQuery;
import com.dick.base.mapper.BaseAuthorityMapper;
import com.dick.base.mapper.BaseAuthorityPathMapper;
import com.dick.base.mapper.BaseRoleMapper;
import com.dick.base.model.BaseAuthority;
import com.dick.base.model.BaseAuthorityPath;
import com.dick.base.model.BaseRole;
import com.dick.base.security.AuthorityNode;
import com.dick.base.security.dto.AuthorityAddParameter;
import com.dick.base.util.CharUtil;
import com.dick.base.util.IdUtil;
import com.dick.base.util.PageInfo;
import com.dick.base.util.SmartQueryUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AuthorityService {

    private BaseRoleMapper baseRoleMapper;
    private BaseAuthorityMapper baseAuthorityMapper;
    private BaseAuthorityPathMapper baseAuthorityPathMapper;

    public AuthorityService(BaseRoleMapper baseRoleMapper, BaseAuthorityMapper baseAuthorityMapper, BaseAuthorityPathMapper baseAuthorityPathMapper) {
        this.baseRoleMapper = baseRoleMapper;
        this.baseAuthorityMapper = baseAuthorityMapper;
        this.baseAuthorityPathMapper = baseAuthorityPathMapper;
    }

    @SmartQuery(clazz = BaseRole.class, likeFields = "name")
    public IPage<BaseRole> rolePage(PageInfo pageInfo, BaseRole baseRole) {
        return baseRoleMapper.selectPage(SmartQueryUtil.getPage(), SmartQueryUtil.getQueryWrapper());
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
        Map<Long, AuthorityNode> nodeMap = new LinkedHashMap<>(authorityList.size() * 2 + 1);
        authorityList.forEach( baseAuthority -> {
            AuthorityNode node = new AuthorityNode();
            node.setId(baseAuthority.getId());
            node.setAuthorityCode(baseAuthority.getAuthorityCode());
            node.setAuthorityType(baseAuthority.getAuthorityType());
            nodeMap.put(baseAuthority.getId(), node);
        });
        Set<Long> rootSet = new LinkedHashSet<>(nodeMap.keySet());
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
        authority.setId(IdUtil.nextId());
        authority.setName(parameter.getName());
        char code = CharUtil.randomChar();
        while (true) {
            BaseAuthority authoritySelect = new BaseAuthority();
            authoritySelect.setAuthorityCode(String.valueOf(code));
            if ( baseAuthorityMapper.selectCount(new QueryWrapper<>(authoritySelect)) == 0 ) {
                break;
            }
            code = CharUtil.randomChar();
        }
        authority.setAuthorityCode(String.valueOf(code));
        baseAuthorityMapper.insert(authority);
        if (parameter.getParentId() != null) {
            baseAuthorityPathMapper.addPath(authority.getId(), parameter.getParentId());
        } else {
            baseAuthorityPathMapper.insert(buildPath(authority.getId(), authority.getId(), 0));
        }
        return authority;
    }

    private BaseAuthorityPath buildPath(Long ancestor, Long descendant, Integer distance) {
        BaseAuthorityPath path = new BaseAuthorityPath();
        path.setDistance(distance);
        path.setAncestor(ancestor);
        path.setDescendant(descendant);
        return path;
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
    public void removeAuthority(Long id) {
        baseAuthorityMapper.deleteById(id);
        QueryWrapper<BaseAuthorityPath> wrapper = new QueryWrapper<>();
        wrapper.eq("ancestor", id).or().eq("descendant", id);
        baseAuthorityPathMapper.delete(wrapper);
    }
}

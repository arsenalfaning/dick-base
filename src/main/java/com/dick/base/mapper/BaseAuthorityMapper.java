package com.dick.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dick.base.model.BaseAuthority;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public interface BaseAuthorityMapper extends BaseMapper<BaseAuthority> {

    @Select("SELECT id,authority_code,authority_type,name FROM base_authority WHERE id IN (SELECT authority_id FROM base_user_authority WHERE user_id = #{userId}) AND deleted = 0")
    List<BaseAuthority> findByUserId(@Param("userId") Long userId);

    @Select("SELECT id,authority_code,authority_type,name FROM base_authority WHERE id IN (SELECT authority_id FROM base_authority_path WHERE ancestor = #{id} AND distance = 1)")
    List<BaseAuthority> findChildrenById(@Param("id") Integer id);

    @Select("SELECT id,authority_code,authority_type,name FROM base_authority WHERE id IN (SELECT authority_id FROM base_authority_path WHERE ancestor = #{id} AND distance >= 1)")
    List<BaseAuthority> findDescendantById(@Param("id") Integer id);

    List<BaseAuthority> findChildrenAndDescendantByIds(@Param("ids1") Collection<Integer> parentIds, @Param("ids2") Collection<Integer> ancestorIds);

}

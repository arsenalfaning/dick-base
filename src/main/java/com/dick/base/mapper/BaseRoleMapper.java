package com.dick.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dick.base.model.BaseRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BaseRoleMapper extends BaseMapper<BaseRole> {

    @Select("SELECT * FROM base_role WHERE id IN (SELECT role_id FROM base_user_role WHERE user_id = #{userId})")
    List<BaseRole> findByUserId(@Param("userId") Long userId);
}

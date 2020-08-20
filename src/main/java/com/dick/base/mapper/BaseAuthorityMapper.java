package com.dick.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dick.base.model.BaseAuthority;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BaseAuthorityMapper extends BaseMapper<BaseAuthority> {

    @Select("SELECT * FROM base_authority WHERE id IN (SELECT authority_id FROM base_user_authority WHERE user_id = #{userId})")
    List<BaseAuthority> findByUserId(@Param("userId") Long userId);
}

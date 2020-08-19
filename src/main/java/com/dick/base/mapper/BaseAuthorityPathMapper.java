package com.dick.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dick.base.model.BaseAuthorityPath;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service
public interface BaseAuthorityPathMapper extends BaseMapper<BaseAuthorityPath> {

    @Insert("INSERT INTO base_authority_path (ancestor,descendant,distance) SELECT ap.ancestor,#{descendant},ap.distance + 1 FROM base_authority_path ap WHERE ap.descendant = #{parentId} UNION ALL SELECT #{descendant},#{descendant},0")
    int addPath(@Param("descendant") Long descendant, @Param("parentId") Long parentId);
}

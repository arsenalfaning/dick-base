package com.dick.base.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("base_authority_path")
@Data
public class BaseAuthorityPath {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Integer ancestor;

    private Integer descendant;

    private Integer distance;
}

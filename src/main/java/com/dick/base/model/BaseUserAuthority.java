package com.dick.base.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("base_user_authority")
@Data
public class BaseUserAuthority {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Long userId;

    private Integer authorityId;
}

package com.dick.base.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("base_role")
@Data
public class BaseRole {

    @TableId()
    private Long id;

    private String name;

    private String roleCode;
}

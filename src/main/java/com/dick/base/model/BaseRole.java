package com.dick.base.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("base_role")
@Data
public class BaseRole {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private String roleCode;

    @TableLogic
    private Boolean deleted;
}

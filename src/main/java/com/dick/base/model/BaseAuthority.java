package com.dick.base.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("base_authority")
@Data
public class BaseAuthority {

    /**
     * 权限类型-自己
     */
    public static final Byte Authority_Type_Single = 0;
    /**
     * 权限类型-自己及儿子节点
     */
    public static final Byte Authority_Type_Children = 1;
    /**
     * 权限类型-自己及所有后代节点
     */
    public static final Byte Authority_Type_Posterity = 2;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private String authorityCode;

    private Byte authorityType;

    @TableLogic
    private Boolean deleted;
}

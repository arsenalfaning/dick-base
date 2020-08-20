package com.dick.base.security;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * 权限节点
 */
@Data
public class AuthorityNode {
    private Integer id;
    private String authorityCode;
    private Byte authorityType;
    private String name;
    private List<AuthorityNode> children = new LinkedList<>();
}

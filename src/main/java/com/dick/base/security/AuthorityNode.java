package com.dick.base.security;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * 权限节点
 */
@Data
public class AuthorityNode {
    private Long id;
    private String authorityCode;
    private Byte authorityType;
    private List<AuthorityNode> children = new LinkedList<>();
}

CREATE TABLE `base_authority` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `authority_code` varchar(32) NOT NULL COMMENT '权限编码',
  `name` varchar(32) NOT NULL COMMENT '权限名',
  `authority_type` tinyint NOT NULL DEFAULT '0' COMMENT '权限类型：0-仅自己 1-自己及儿子节点 2-自己及所有后代节点',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除:0未删除 1已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `authority_code_UNIQUE` (`authority_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

CREATE TABLE `base_authority_path` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `ancestor` int unsigned NOT NULL COMMENT '祖宗节点',
  `descendant` int unsigned NOT NULL COMMENT '后代节点',
  `distance` int NOT NULL DEFAULT '0' COMMENT '两节点间距离',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ancestor_UNIQUE` (`ancestor`,`descendant`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='基本权限关系表';

CREATE TABLE `base_role` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_code` varchar(32) NOT NULL COMMENT '角色编码，唯一的unicode字符',
  `name` varchar(32) NOT NULL COMMENT '角色名',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除:0未删除 1已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_code_UNIQUE` (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

CREATE TABLE `base_user` (
  `id` bigint unsigned NOT NULL COMMENT '用户id',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `password` char(64) NOT NULL COMMENT '密码',
  `salt` char(32) NOT NULL COMMENT '盐',
  `sign_up_time` datetime DEFAULT NULL COMMENT '注册时间',
  `sign_in_token` char(32) DEFAULT NULL COMMENT '登录的token，限单个客户端登录',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='基本用户表';

CREATE TABLE `base_user_authority` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户id',
  `authority_id` int NOT NULL COMMENT '权限id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_user_authority` (`user_id`,`authority_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='用户权限关系表';

CREATE TABLE `base_user_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户id',
  `role_id` int NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_user_role` (`user_id`,`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关系表';

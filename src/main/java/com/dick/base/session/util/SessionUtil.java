package com.dick.base.session.util;

import com.dick.base.security.BaseUserDetails;
import com.dick.base.session.dto.UserBaseInfo;
import org.springframework.security.core.context.SecurityContextHolder;

public class SessionUtil {

    /**
     * 获取当前登录的用户
     * @return
     */
    public static UserBaseInfo currentUser() {
        BaseUserDetails userDetails = (BaseUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails == null) return null;
        return userDetails.getUser();
    }
}

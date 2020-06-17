package com.dick.base.util;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.apache.commons.lang3.StringUtils;

public class TokenUtil {

    public static String generateUserToken() {
        return IdWorker.get32UUID();
    }

    public static String generateRedisToken(String token) {
        return StringUtils.reverse(token);
    }
}

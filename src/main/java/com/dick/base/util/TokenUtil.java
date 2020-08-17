package com.dick.base.util;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;

public class TokenUtil {

    public static String generateUserToken() {
        return IdWorker.get32UUID();
    }

}

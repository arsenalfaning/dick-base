package com.dick.base.util;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;

public class IdUtil {

    public static Long nextId() {
        return IdWorker.getId();
    }
}

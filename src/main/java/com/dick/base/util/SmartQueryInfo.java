package com.dick.base.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class SmartQueryInfo {
    private QueryWrapper queryWrapper;
    private Page page;
}

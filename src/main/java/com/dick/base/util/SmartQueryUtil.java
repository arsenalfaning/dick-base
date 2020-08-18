package com.dick.base.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.LinkedList;

public class SmartQueryUtil {

    private static final ThreadLocal<SmartQueryInfo> QUERY_WRAPPER_THREAD_LOCAL = new ThreadLocal<>();

    public static SmartQueryInfo get() {
        SmartQueryInfo queryInfo = QUERY_WRAPPER_THREAD_LOCAL.get();
        if (queryInfo == null) {
            queryInfo = new SmartQueryInfo();
            queryInfo.setQueryWrapper(new QueryWrapper());
            QUERY_WRAPPER_THREAD_LOCAL.set(queryInfo);
        }
        return queryInfo;
    }

    public static QueryWrapper getQueryWrapper() {
        SmartQueryInfo queryInfo = get();
        return queryInfo.getQueryWrapper();
    }

    public static Page getPage() {
        return get().getPage();
    }

    public static void remove() {
        QUERY_WRAPPER_THREAD_LOCAL.remove();
    }

    public static <T, Y> Page<Y> changePageType(Page<T> page, TypeChange<T, Y> typeChange) {
        Page<Y> pageNew = new Page<>(page.getCurrent(), page.getSize());
        pageNew.setTotal(page.getTotal());
        pageNew.setRecords(new LinkedList<>());
        page.getRecords().stream().forEach(t -> {pageNew.getRecords().add(typeChange.change(t));});
        pageNew.setOrders(page.getOrders());
        return pageNew;
    }
}

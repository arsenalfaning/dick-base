package com.dick.base.util;

import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.TreeSet;

public class AuthorityUtil {

    /**
     * 把集合的string解析为集合
     * @param list
     * @return
     */
    public static Set<String> decode(String list) {
        Set<String> set = new TreeSet<>();
        if (!StringUtils.isEmpty(list)) {
            char[] arr = list.toCharArray();
            for (char c : arr) {
                set.add(String.valueOf(c));
            }
        }
        return set;
    }
}

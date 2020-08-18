package com.dick.base.util;

@FunctionalInterface
public interface TypeChange<T, Y> {

    Y change(T t);
}

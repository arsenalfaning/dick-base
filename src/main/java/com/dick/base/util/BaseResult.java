package com.dick.base.util;

import lombok.Data;

@Data
public class BaseResult <T>{

    public static <S> BaseResult<S> of(S s) {
        BaseResult<S> baseResult = new BaseResult<>();
        baseResult.setData(s);
        return baseResult;
    }

    public static final BaseResult<Void> voidResult() {
        return new BaseResult<>();
    }

    public static final BaseResult<Void> errorResult(String message) {
        BaseResult<Void> baseResult = new BaseResult<>();
        baseResult.setMessage(message);
        return baseResult;
    }

    private T data;
    private String message = "success";
}

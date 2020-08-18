package com.dick.base.util;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class PageInfo {

    @Min(1)
    private int page = 1;

    @Max(100)
    private int size = 10;

    private String order;

    private String prop;
}

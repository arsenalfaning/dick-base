package com.dick.base.session.dto;

import com.dick.base.util.DateUtil;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SignInResult {

    private LocalDateTime time;

    private String timeDesc;

    public String getTimeDesc() {
        return DateUtil.format(time);
    }
}
